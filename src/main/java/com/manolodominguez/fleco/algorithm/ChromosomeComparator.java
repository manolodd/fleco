/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.manolodominguez.fleco.algorithm;

import com.manolodominguez.fleco.genetic.Chromosome;
import java.util.Comparator;

/**
 *
 * @author manolodd
 */
public class ChromosomeComparator implements Comparator<Chromosome> {

    @Override
    public int compare(Chromosome chromosome1, Chromosome chromosome2) {
        if (chromosome1.getFitness() < chromosome2.getFitness()) {
            return 1;
        }
        if (chromosome1.getFitness() > chromosome2.getFitness()) {
            return -1;
        }
        return 0;
    }

}
