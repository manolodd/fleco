# PROJECT STATUS

## Master branch:

![Maven build master](https://github.com/manolodd/fleco/actions/workflows/maven-publish.yml/badge.svg?branch=master)

## Development branch:

![Maven build master](https://github.com/manolodd/fleco/actions/workflows/maven-publish.yml/badge.svg?branch=development)

# THE PROJECT

<b>FLECO</b> (Fast, Lightweight, and Efficient Cybersecurity Optimization) (1) Adaptive, Constrained, and Multi-objective Genetic Algorithm is a genetic algorithm designed to assist the Asset's Cybersecurity Committee (ACC) in making decisions during the application of CyberTOMP (2), aimed at managing comprehensive cybersecurity at both tactical and operational levels.

It serves as a library that can be seamlessly incorporated into larger solutions to facilitate testing. However, it is accompanied by FLECO Studio, a comprehensive GUI-based solution that is highly recommended for the committee responsible for selecting the necessary cybersecurity measures as defined in CyberTOMP. FLECO Studio enables this committee to conduct multiple simulations, preserve the current system state and the desired target state, load previously saved cases from storage, and seamlessly resume their work.

1. Domínguez-Dorado, M.; Cortés-Polo, D.; Carmona-Murillo, J.; Rodríguez-Pérez, F.J.; Galeano-Brajones, J. Fast, Lightweight, and Efficient Cybersecurity Optimization for Tactical–Operational Management. Appl. Sci. 2023, 13, 6327. https://doi.org/10.3390/app13106327
2. Dominguez-Dorado, M., Carmona-Murillo, J., Cortés-Polo, D., and Rodríguez-Pérez, F. J. (2022). CyberTOMP: A novel systematic framework to manage asset-focused cybersecurity from tactical and operational levels. IEEE Access, 10, 122454-122485. https://doi.org/10.1109/ACCESS.2022.3223440

# LICENSE

## Latest snapshot version being developed:

- <b>FLECO 1.3-SNAPSHOT</b> (develop branch) - LGPL-3.0-or-later.

## Binary releases:
 
- <b>FLECO 1.2</b> (current, master branch) - LGPL-3.0-or-later.
- <b>FLECO 1.1</b> LGPL-3.0-or-later.
- <b>FLECO 1.0</b> LGPL-3.0-or-later.

# PEOPLE BEHIND OPENSIMMPLS

## Author:
    
 - Manuel Domínguez-Dorado - <ingeniero@ManoloDominguez.com>
   
# COMPILING FROM SOURCES

The best option is to download latest compiled stable releases from the releases section of this repository. However, if you want to test new features (please, do it and give feedback), you will need to compile the project from sources. Follow these steps:

 - Clone the FLECO repo: 
```console
git clone https://github.com/manolodd/fleco.git
```
 - Compile the code and obtain a binary jar including all you need (you will need to install Maven before):
```console
cd fleco
mvn package
```
 - The jar file will be located in "target" directory.
```console
cd target
```
- Now, run the simulator:
```console
java -jar fleco-{YourVersion}-with-dependencies.jar
```
# THIRD-PARTY COMPONENTS

OpenSimMPLS uses third-party components each one of them having its own OSS license. License compatibility has been taken into account to allow OpenSimMPLS be released under its current OSS licence. They are:

- miglayout-swing 11.1 - BSD-3-clause - https://github.com/mikaelgrev/miglayout
- miglayout-core 11.1 - BSD-3-clause - https://github.com/mikaelgrev/miglayout
- everit-json-schema 1.14.2 - Apache-2.0 - https://github.com/everit-org/json-schema

Thanks folks!

# USING FLECO IN JAVA LIBRARY MODE

FLECO is quite simple to use in library mode. Download and include the artifact in your project. Then:

- Define the main algorithm's parameters:

```java
// Number of potential solutions
int initialPopulation = 30;  

// Seconds before stopping if no solutions are found
int maxSeconds = 5 * 60;  

// A standar crossover probability in the range [0.0f - 1.0f]
float crossoverProbability = 0.90f;  

// Select IG1, IG2, IG3 depending on whether the corresponding asset requires
// LOW, MEDIUM or HIGH cybersecurity.
ImplementationGroups implementationGroup = ImplementationGroups.IG3; 
```

- Next, create and define your asset's current cybersecurity status according
to CyberTOMP proposal. You must configure each chromosome's allele's value 
individually to fit the real state of your asset's expected outcome. Thi should 
be done through squential calls to updateAllele(gene, allele). For instance:

```java
Chromosome initialStatus = new Chromosome(implementationGroup);
initialStatus.updateAllele(Genes.PR_AC_PR_AC_3, Alleles.DLI_67);
initialStatus.updateAllele(Genes.PR_AC_PR_AC_4, Alleles.DLI_67);
initialStatus.updateAllele(Genes.PR_AC_PR_AC_5, Alleles.DLI_0);
initialStatus.updateAllele(Genes.PR_AC_PR_AC_7, Alleles.DLI_67);
initialStatus.updateAllele(Genes.PR_AT_PR_AT_1, Alleles.DLI_0);
initialStatus.updateAllele(Genes.PR_DS_CSC_3_4, Alleles.DLI_100);
initialStatus.updateAllele(Genes.PR_DS_PR_DS_3, Alleles.DLI_67);
initialStatus.updateAllele(Genes.PR_IP_9D_9, Alleles.DLI_0);
initialStatus.updateAllele(Genes.PR_IP_CSC_11_1, Alleles.DLI_100);
initialStatus.updateAllele(Genes.PR_IP_CSC_4_3, Alleles.DLI_100); 
```

and so on...



# USING FLECO STUDIO (JAVA SWING STANDALONE APPLICATION MODE)

