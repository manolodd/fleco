/* 
 * Open Licensing Risk Analysis Engine (Open LRAE) is a licensing risk analysis 
 * engine in the form of Java library that allow the detection of risks related 
 * to licensing from the set of components (and their respective licenses) you
 * are using in a given project.
 * 
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
 */
package com.manolodominguez.fleco.main.experiments.statuses;

import com.manolodominguez.fleco.genetic.Alleles;
import com.manolodominguez.fleco.genetic.Chromosome;
import com.manolodominguez.fleco.genetic.Genes;
import com.manolodominguez.fleco.uleo.ImplementationGroups;

/**
 * This class implement a predefined initial cybersecurity status for a
 * ficticious asset where the applicable implementation group is IG1. It is
 * intended only to ease the experiments.
 *
 * @author Manuel Domínguez-Dorado
 */
public class InitialStatusForIG1 extends Chromosome {

    /**
     * This is the constuctor of the class. It creates a new initial
     * cybersecurity status for a ficticious asset where the applicable
     * implementation group is IG1.
     *
     * @author Manuel Domínguez-Dorado
     */
    public InitialStatusForIG1() {
        super(ImplementationGroups.IG1);
        updateAllele(Genes.ID_AM_CSC_1_1, Alleles.DLI_33);
        updateAllele(Genes.ID_AM_CSC_14_1, Alleles.DLI_67);
        updateAllele(Genes.ID_AM_CSC_2_2, Alleles.DLI_0);
        updateAllele(Genes.ID_AM_CSC_3_1, Alleles.DLI_0);
        updateAllele(Genes.ID_AM_CSC_3_2, Alleles.DLI_33);
        updateAllele(Genes.ID_AM_CSC_3_6, Alleles.DLI_0);
        updateAllele(Genes.ID_AM_ID_AM_1, Alleles.DLI_67);
        updateAllele(Genes.ID_AM_ID_AM_2, Alleles.DLI_0);
        updateAllele(Genes.ID_GV_ID_GV_1, Alleles.DLI_100);
        updateAllele(Genes.ID_RA_ID_RA_1, Alleles.DLI_0);
        updateAllele(Genes.ID_SC_ID_SC_2, Alleles.DLI_100);
        updateAllele(Genes.ID_SC_ID_SC_5, Alleles.DLI_67);
        updateAllele(Genes.PR_AC_CSC_4_7, Alleles.DLI_33);
        updateAllele(Genes.PR_AC_CSC_5_2, Alleles.DLI_33);
        updateAllele(Genes.PR_AC_PR_AC_1, Alleles.DLI_33);
        updateAllele(Genes.PR_AC_PR_AC_3, Alleles.DLI_67);
        updateAllele(Genes.PR_AC_PR_AC_4, Alleles.DLI_67);
        updateAllele(Genes.PR_AC_PR_AC_5, Alleles.DLI_0);
        updateAllele(Genes.PR_AC_PR_AC_7, Alleles.DLI_67);
        updateAllele(Genes.PR_AT_PR_AT_1, Alleles.DLI_0);
        updateAllele(Genes.PR_DS_CSC_3_4, Alleles.DLI_100);
        updateAllele(Genes.PR_DS_PR_DS_3, Alleles.DLI_67);
        updateAllele(Genes.PR_IP_9D_9, Alleles.DLI_0);
        updateAllele(Genes.PR_IP_CSC_11_1, Alleles.DLI_100);
        updateAllele(Genes.PR_IP_CSC_4_3, Alleles.DLI_100);
        updateAllele(Genes.PR_IP_PR_IP_1, Alleles.DLI_33);
        updateAllele(Genes.PR_IP_PR_IP_11, Alleles.DLI_100);
        updateAllele(Genes.PR_IP_PR_IP_4, Alleles.DLI_100);
        updateAllele(Genes.PR_IP_PR_IP_6, Alleles.DLI_33);
        updateAllele(Genes.PR_IP_PR_IP_9, Alleles.DLI_33);
        updateAllele(Genes.PR_MA_CSC_12_1, Alleles.DLI_100);
        updateAllele(Genes.PR_MA_CSC_4_2, Alleles.DLI_33);
        updateAllele(Genes.PR_MA_CSC_4_6, Alleles.DLI_100);
        updateAllele(Genes.PR_MA_CSC_7_3, Alleles.DLI_0);
        updateAllele(Genes.PR_MA_CSC_8_1, Alleles.DLI_0);
        updateAllele(Genes.PR_MA_CSC_8_3, Alleles.DLI_33);
        updateAllele(Genes.PR_PT_CSC_4_4, Alleles.DLI_67);
        updateAllele(Genes.PR_PT_CSC_4_5, Alleles.DLI_33);
        updateAllele(Genes.PR_PT_PR_PT_1, Alleles.DLI_67);
        updateAllele(Genes.PR_PT_PR_PT_2, Alleles.DLI_67);
        updateAllele(Genes.PR_PT_PR_PT_5, Alleles.DLI_67);
        updateAllele(Genes.DE_AE_DE_AE_3, Alleles.DLI_33);
        updateAllele(Genes.DE_CM_DE_CM_4, Alleles.DLI_100);
        updateAllele(Genes.DE_CM_DE_CM_7, Alleles.DLI_0);
        updateAllele(Genes.DE_DP_CSC_17_1, Alleles.DLI_100);
        updateAllele(Genes.RS_CO_CSC_17_4, Alleles.DLI_33);
        updateAllele(Genes.RS_MI_CSC_1_2, Alleles.DLI_33);
    }

}
