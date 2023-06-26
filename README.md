# THE PROJECT

<b>FLECO</b> (Fast, Lightweight, and Efficient Cybersecurity Optimization) (1) Adaptive, Constrained, and Multi-objective Genetic Algorithm is a genetic algorithm designed to assist the Asset's Cybersecurity Committee (ACC) in making decisions during the application of CyberTOMP (2), aimed at managing comprehensive cybersecurity at both tactical and operational levels.

(1) Domínguez-Dorado, M.; Cortés-Polo, D.; Carmona-Murillo, J.; Rodríguez-Pérez, F.J.; Galeano-Brajones, J. Fast, Lightweight, and Efficient Cybersecurity Optimization for Tactical–Operational Management. Appl. Sci. 2023, 13, 6327. https://doi.org/10.3390/app13106327
(2) Dominguez-Dorado, M., Carmona-Murillo, J., Cortés-Polo, D., and Rodríguez-Pérez, F. J. (2022). CyberTOMP: A novel systematic framework to manage asset-focused cybersecurity from tactical and operational levels. IEEE Access, 10, 122454-122485. https://doi.org/10.1109/ACCESS.2022.3223440

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

