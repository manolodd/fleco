/* 
 *******************************************************************************
 * FLECO (Fast, Lightweight, and Efficient Cybersecurity Optimization) (1) 
 * Adaptive, Constrained, and Multi-objective Genetic Algorithm is a genetic 
 * algorithm designed to assist the Asset's Cybersecurity Committee (ACC) in 
 * making decisions during the application of CyberTOMP (2), aimed at managing 
 * comprehensive cybersecurity at both tactical and operational levels.
 *
 * (1) Domínguez-Dorado, M.; Cortés-Polo, D.; Carmona-Murillo, J.; 
 * Rodríguez-Pérez, F.J.; Galeano-Brajones, J. Fast, Lightweight, and Efficient 
 * Cybersecurity Optimization for Tactical–Operational Management. Appl. Sci. 
 * 2023, 13, 6327. https://doi.org/10.3390/app13106327
 *
 * (2) Dominguez-Dorado, M., Carmona-Murillo, J., Cortés-Polo, D., and
 * Rodríguez-Pérez, F. J. (2022). CyberTOMP: A novel systematic framework to
 * manage asset-focused cybersecurity from tactical and operational levels. IEEE
 * Access, 10, 122454-122485. https://doi.org/10.1109/ACCESS.2022.3223440
 *******************************************************************************
 * Copyright (C) Manuel Domínguez Dorado - ingeniero@ManoloDominguez.com.
 * 
 * This program is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more 
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this program. If not, see 
 * https://www.gnu.org/licenses/lgpl-3.0.en.html.
 *******************************************************************************
 */
package com.manolodominguez.fleco.gui.flecoio;

import com.manolodominguez.fleco.genetics.Alleles;
import com.manolodominguez.fleco.genetics.Chromosome;
import com.manolodominguez.fleco.genetics.Genes;
import com.manolodominguez.fleco.gui.AvailableResources;
import com.manolodominguez.fleco.strategicconstraints.ComparisonOperators;
import com.manolodominguez.fleco.strategicconstraints.Constraint;
import com.manolodominguez.fleco.strategicconstraints.StrategicConstraints;
import com.manolodominguez.fleco.uleo.Categories;
import com.manolodominguez.fleco.uleo.Functions;
import com.manolodominguez.fleco.uleo.ImplementationGroups;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements FLECO file loader that can restore a case saved on disk
 * to memory.
 *
 * @author Manuel Domínguez-Dorado
 */
public class FLECOLoader {

    private Chromosome initialStatus;
    private StrategicConstraints strategicConstraints;
    private Chromosome targetStatus;
    private FileInputStream inputStream;
    private BufferedReader input;
    private ImplementationGroups implementationGroup;

    private final Logger logger = LoggerFactory.getLogger(FLECOLoader.class);
    
    /**
     * This is the constructor of the class. It creates a new FLECO loader and
     * sets its initial values.
     *
     * @author Manuel Domínguez-Dorado
     */
    public FLECOLoader() {
        inputStream = null;
        input = null;
        implementationGroup = null;
        initialStatus = null;
        strategicConstraints = null;
        targetStatus = null;
    }

    /**
     * This method loads the content of the file specified as an argument and,
     * if the content complies with FLECO format, the case is loaded into
     * memory.
     *
     * @author Manuel Domínguez-Dorado
     * @param inputFile the file being loaded.
     * @return true, if a FLECO case is loaded from the specified file.
     * Otherwise, false.
     */
    public boolean load(File inputFile) {
        boolean loaded = false;
        if (inputFile == null) {
            logger.error("inputFile is null");
            throw new IllegalArgumentException("inputFile is null");
        }
        JSONObject jsonFLECOCase;
        try {
            jsonFLECOCase = new JSONObject(new JSONTokener(inputFile.toURI().toURL().openStream()));
            try {
                if (isValidJSONFLECOCase(jsonFLECOCase)) {
                    return initializeFromJSON(jsonFLECOCase);
                }
            }
            catch (RuntimeException e) {
                loaded = false;
            }
        }
        catch (MalformedURLException ex) {
            loaded = false;
        }
        catch (IOException | JSONException ex) {
            loaded = false;
        }
        return loaded;
    }

    /**
     * This method load a FLECO case that is expressed in the forma of a valid
     * JSON object into memory.
     *
     * @author Manuel Domínguez-Dorado
     * @param validatedJSONFLECOCase the JSON object that represent a valid
     * FLECO case.
     * @return true, if a FLECO case is loaded from the specified JSON object.
     * Otherwise, false.
     */
    private boolean initializeFromJSON(JSONObject validatedJSONFLECOCase) {
        boolean hasTargetStatus = false;
        if (validatedJSONFLECOCase == null) {
            logger.error("validatedJSONFLECOCase cannot be null");
            throw new IllegalArgumentException("validatedJSONFLECOCase cannot be null");
        }
        try {
            implementationGroup = ImplementationGroups.valueOf(validatedJSONFLECOCase.getString("caseIG"));
            hasTargetStatus = validatedJSONFLECOCase.getBoolean("hasTargetStatus");
            initialStatus = new Chromosome(implementationGroup);
            JSONArray savedInitialGenes = validatedJSONFLECOCase.getJSONArray("initialStatus");
            for (int i = 0; i < savedInitialGenes.length(); i++) {
                Genes initialGene = Genes.valueOf(savedInitialGenes.getJSONObject(i).getString("gene"));
                Alleles initialAllele = Alleles.valueOf(savedInitialGenes.getJSONObject(i).getString("allele"));
                initialStatus.updateAllele(initialGene, initialAllele);
            }
            strategicConstraints = new StrategicConstraints(implementationGroup);
            JSONArray savedConstraints = validatedJSONFLECOCase.getJSONArray("strategicConstraints");
            for (int i = 0; i < savedConstraints.length(); i++) {
                Genes constraintGene;
                Functions constraintFunction;
                Categories constraintCategory;
                Float constraintValue = savedConstraints.getJSONObject(i).getFloat("value");
                ComparisonOperators constraintOperator = ComparisonOperators.valueOf(savedConstraints.getJSONObject(i).getString("operator"));
                Constraint constraint = new Constraint(constraintOperator, constraintValue);
                try {
                    constraintGene = Genes.valueOf(savedConstraints.getJSONObject(i).getString("gene"));
                    strategicConstraints.addConstraint(constraintGene, constraint);
                }
                catch (JSONException ex) {
                    try {
                        constraintFunction = Functions.valueOf(savedConstraints.getJSONObject(i).getString("function"));
                        strategicConstraints.addConstraint(constraintFunction, constraint);
                    }
                    catch (JSONException ex2) {
                        try {
                            constraintCategory = Categories.valueOf(savedConstraints.getJSONObject(i).getString("category"));
                            strategicConstraints.addConstraint(constraintCategory, constraint);
                        }
                        catch (JSONException ex3) {
                            try {
                                savedConstraints.getJSONObject(i).getString("asset");
                                strategicConstraints.addConstraint(constraint);
                            }
                            catch (JSONException ex4) {
                                return false;
                            }
                        }
                    }
                }
            }
            if (hasTargetStatus) {
                targetStatus = new Chromosome(implementationGroup);
                JSONArray savedTargetGenes = validatedJSONFLECOCase.getJSONArray("targetStatus");
                for (int i = 0; i < savedTargetGenes.length(); i++) {
                    Genes targetGene = Genes.valueOf(savedTargetGenes.getJSONObject(i).getString("gene"));
                    Alleles targetAllele = Alleles.valueOf(savedTargetGenes.getJSONObject(i).getString("allele"));
                    targetStatus.updateAllele(targetGene, targetAllele);
                }
            }
            return true;
        }
        catch (JSONException ex) {
            return false;
        }
    }

    /**
     * This method check whether the JSON object specified as an argument is a
     * valid FLECO case.
     *
     * @author Manuel Domínguez-Dorado
     * @param jsonFLECOCase the JSON object that represent, allegedly, a valid
     * FLECO case.
     * @return true, if the specified jsonFLECOCase object is a valid FLECO
     * case. Otherwise, false.
     */
    private boolean isValidJSONFLECOCase(JSONObject jsonFLECOCase) {
        if (jsonFLECOCase == null) {
            logger.error("Case cannot be null");
            throw new IllegalArgumentException("Case cannot be null");
        }
        try {
            JSONObject rawSchema = new JSONObject(new JSONTokener(getClass().getResourceAsStream(AvailableResources.FLECO_JSON_SCHEMA.getResource())));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(jsonFLECOCase); // throws a ValidationException if this object is invalid
            return true;
        }
        catch (ValidationException ex) {
            return false;
        }
    }

    /**
     * This method return the initial status corresponding to the loaded FLECO
     * case.
     *
     * @author Manuel Domínguez-Dorado
     * @return the initial status corresponding to the loaded FLECO case.
     */
    public Chromosome getInitialStatus() {

        return initialStatus;
    }

    /**
     * This method return the strategic constraints corresponding to the loaded
     * FLECO case.
     *
     * @author Manuel Domínguez-Dorado
     * @return the strategic constraints corresponding to the loaded FLECO case.
     */
    public StrategicConstraints getStrategicConstraints() {
        return strategicConstraints;
    }

    /**
     * This method return the target status corresponding to the loaded FLECO
     * case.
     *
     * @author Manuel Domínguez-Dorado
     * @return the target status corresponding to the loaded FLECO case.
     */
    public Chromosome getTargetStatus() {
        return targetStatus;
    }

}
