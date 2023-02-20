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
package com.manolodominguez.fleco.genetic;

import com.manolodominguez.fleco.uleo.Categories;
import com.manolodominguez.fleco.uleo.ImplementationGroups;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This enum defines all cyebrsecurity expected outcomes, in the form of genes,
 * and also its weights as defined in CyberTOMP proposal, depending on whether
 * implementation groups 1, 2, or 3 applies.
 *
 * @author manuel Domínguez-Dorado
 */
public enum Genes {
    ID_AM_CSC_1_1((float) 1 / 8, (float) 1 / 11, (float) 1 / 11, ImplementationGroups.IG1, Categories.ID_AM),
    ID_AM_CSC_12_4((float) 0.0f, (float) 1 / 11, (float) 1 / 11, ImplementationGroups.IG2, Categories.ID_AM),
    ID_AM_CSC_14_1((float) 1 / 8, (float) 1 / 11, (float) 1 / 11, ImplementationGroups.IG1, Categories.ID_AM),
    ID_AM_CSC_2_2((float) 1 / 8, (float) 1 / 11, (float) 1 / 11, ImplementationGroups.IG1, Categories.ID_AM),
    ID_AM_CSC_3_1((float) 1 / 8, (float) 1 / 11, (float) 1 / 11, ImplementationGroups.IG1, Categories.ID_AM),
    ID_AM_CSC_3_2((float) 1 / 8, (float) 1 / 11, (float) 1 / 11, ImplementationGroups.IG1, Categories.ID_AM),
    ID_AM_CSC_3_6((float) 1 / 8, (float) 1 / 11, (float) 1 / 11, ImplementationGroups.IG1, Categories.ID_AM),
    ID_AM_CSC_3_7((float) 0.0f, (float) 1 / 11, (float) 1 / 11, ImplementationGroups.IG2, Categories.ID_AM),
    ID_AM_ID_AM_1((float) 1 / 8, (float) 1 / 11, (float) 1 / 11, ImplementationGroups.IG1, Categories.ID_AM),
    ID_AM_ID_AM_2((float) 1 / 8, (float) 1 / 11, (float) 1 / 11, ImplementationGroups.IG1, Categories.ID_AM),
    ID_AM_ID_AM_3((float) 0.0f, (float) 1 / 11, (float) 1 / 11, ImplementationGroups.IG2, Categories.ID_AM),
    ID_BE_9D_1((float) 0.0f, (float) 1 / 1, (float) 1 / 6, ImplementationGroups.IG2, Categories.ID_BE),
    ID_BE_ID_BE_1((float) 0.0f, (float) 0.0f, (float) 1 / 6, ImplementationGroups.IG3, Categories.ID_BE),
    ID_BE_ID_BE_2((float) 0.0f, (float) 0.0f, (float) 1 / 6, ImplementationGroups.IG3, Categories.ID_BE),
    ID_BE_ID_BE_3((float) 0.0f, (float) 0.0f, (float) 1 / 6, ImplementationGroups.IG3, Categories.ID_BE),
    ID_BE_ID_BE_4((float) 0.0f, (float) 0.0f, (float) 1 / 6, ImplementationGroups.IG3, Categories.ID_BE),
    ID_BE_ID_BE_5((float) 0.0f, (float) 0.0f, (float) 1 / 6, ImplementationGroups.IG3, Categories.ID_BE),
    ID_GV_CSC_17_4((float) 0.0f, (float) 1 / 3, (float) 1 / 5, ImplementationGroups.IG2, Categories.ID_GV),
    ID_GV_ID_GV_1((float) 1 / 1, (float) 1 / 3, (float) 1 / 5, ImplementationGroups.IG1, Categories.ID_GV),
    ID_GV_ID_GV_2((float) 0.0f, (float) 1 / 3, (float) 1 / 5, ImplementationGroups.IG2, Categories.ID_GV),
    ID_GV_ID_GV_3((float) 0.0f, (float) 0.0f, (float) 1 / 5, ImplementationGroups.IG3, Categories.ID_GV),
    ID_GV_ID_GV_4((float) 0.0f, (float) 0.0f, (float) 1 / 5, ImplementationGroups.IG3, Categories.ID_GV),
    ID_RA_9D_1((float) 0.0f, (float) 1 / 4, (float) 1 / 9, ImplementationGroups.IG2, Categories.ID_RA),
    ID_RA_CSC_18_2((float) 0.0f, (float) 1 / 4, (float) 1 / 9, ImplementationGroups.IG2, Categories.ID_RA),
    ID_RA_CSC_18_5((float) 0.0f, (float) 0.0f, (float) 1 / 9, ImplementationGroups.IG3, Categories.ID_RA),
    ID_RA_CSC_3_7((float) 0.0f, (float) 1 / 4, (float) 1 / 9, ImplementationGroups.IG2, Categories.ID_RA),
    ID_RA_ID_RA_1((float) 1 / 1, (float) 1 / 4, (float) 1 / 9, ImplementationGroups.IG1, Categories.ID_RA),
    ID_RA_ID_RA_2((float) 0.0f, (float) 0.0f, (float) 1 / 9, ImplementationGroups.IG3, Categories.ID_RA),
    ID_RA_ID_RA_3((float) 0.0f, (float) 0.0f, (float) 1 / 9, ImplementationGroups.IG3, Categories.ID_RA),
    ID_RA_ID_RA_4((float) 0.0f, (float) 0.0f, (float) 1 / 9, ImplementationGroups.IG3, Categories.ID_RA),
    ID_RA_ID_RA_6((float) 0.0f, (float) 0.0f, (float) 1 / 9, ImplementationGroups.IG3, Categories.ID_RA),
    ID_RM_9D_8((float) 0.0f, (float) 1 / 1, (float) 1 / 4, ImplementationGroups.IG2, Categories.ID_RM),
    ID_RM_ID_RM_1((float) 0.0f, (float) 0.0f, (float) 1 / 4, ImplementationGroups.IG3, Categories.ID_RM),
    ID_RM_ID_RM_2((float) 0.0f, (float) 0.0f, (float) 1 / 4, ImplementationGroups.IG3, Categories.ID_RM),
    ID_RM_ID_RM_3((float) 0.0f, (float) 0.0f, (float) 1 / 4, ImplementationGroups.IG3, Categories.ID_RM),
    ID_SC_ID_SC_1((float) 0.0f, (float) 1 / 4, (float) 1 / 5, ImplementationGroups.IG2, Categories.ID_SC),
    ID_SC_ID_SC_2((float) 1 / 2, (float) 1 / 4, (float) 1 / 5, ImplementationGroups.IG1, Categories.ID_SC),
    ID_SC_ID_SC_3((float) 0.0f, (float) 1 / 4, (float) 1 / 5, ImplementationGroups.IG2, Categories.ID_SC),
    ID_SC_ID_SC_4((float) 0.0f, (float) 0.0f, (float) 1 / 5, ImplementationGroups.IG3, Categories.ID_SC),
    ID_SC_ID_SC_5((float) 1 / 2, (float) 1 / 4, (float) 1 / 5, ImplementationGroups.IG1, Categories.ID_SC),
    PR_AC_CSC_12_5((float) 0.0f, (float) 1 / 11, (float) 1 / 14, ImplementationGroups.IG2, Categories.PR_AC),
    PR_AC_CSC_12_6((float) 0.0f, (float) 1 / 11, (float) 1 / 14, ImplementationGroups.IG2, Categories.PR_AC),
    PR_AC_CSC_13_4((float) 0.0f, (float) 1 / 11, (float) 1 / 14, ImplementationGroups.IG2, Categories.PR_AC),
    PR_AC_CSC_4_7((float) 1 / 7, (float) 1 / 11, (float) 1 / 14, ImplementationGroups.IG1, Categories.PR_AC),
    PR_AC_CSC_5_2((float) 1 / 7, (float) 1 / 11, (float) 1 / 14, ImplementationGroups.IG1, Categories.PR_AC),
    PR_AC_CSC_5_6((float) 0.0f, (float) 1 / 11, (float) 1 / 14, ImplementationGroups.IG2, Categories.PR_AC),
    PR_AC_CSC_6_8((float) 0.0f, (float) 0.0f, (float) 1 / 14, ImplementationGroups.IG3, Categories.PR_AC),
    PR_AC_PR_AC_1((float) 1 / 7, (float) 1 / 11, (float) 1 / 14, ImplementationGroups.IG1, Categories.PR_AC),
    PR_AC_PR_AC_2((float) 0.0f, (float) 0.0f, (float) 1 / 14, ImplementationGroups.IG3, Categories.PR_AC),
    PR_AC_PR_AC_3((float) 1 / 7, (float) 1 / 11, (float) 1 / 14, ImplementationGroups.IG1, Categories.PR_AC),
    PR_AC_PR_AC_4((float) 1 / 7, (float) 1 / 11, (float) 1 / 14, ImplementationGroups.IG1, Categories.PR_AC),
    PR_AC_PR_AC_5((float) 1 / 7, (float) 1 / 11, (float) 1 / 14, ImplementationGroups.IG1, Categories.PR_AC),
    PR_AC_PR_AC_6((float) 0.0f, (float) 0.0f, (float) 1 / 14, ImplementationGroups.IG3, Categories.PR_AC),
    PR_AC_PR_AC_7((float) 1 / 7, (float) 1 / 11, (float) 1 / 14, ImplementationGroups.IG1, Categories.PR_AC),
    PR_AT_CSC_14_9((float) 0.0f, (float) 1 / 4, (float) 1 / 4, ImplementationGroups.IG2, Categories.PR_AT),
    PR_AT_CSC_15_4((float) 0.0f, (float) 1 / 4, (float) 1 / 4, ImplementationGroups.IG2, Categories.PR_AT),
    PR_AT_PR_AT_1((float) 1 / 1, (float) 1 / 4, (float) 1 / 4, ImplementationGroups.IG1, Categories.PR_AT),
    PR_AT_PR_AT_2((float) 0.0f, (float) 1 / 4, (float) 1 / 4, ImplementationGroups.IG2, Categories.PR_AT),
    PR_DS_9D_6((float) 0.0f, (float) 0.0f, (float) 1 / 10, ImplementationGroups.IG3, Categories.PR_DS),
    PR_DS_CSC_3_4((float) 1 / 2, (float) 1 / 6, (float) 1 / 10, ImplementationGroups.IG1, Categories.PR_DS),
    PR_DS_PR_DS_1((float) 0.0f, (float) 1 / 6, (float) 1 / 10, ImplementationGroups.IG2, Categories.PR_DS),
    PR_DS_PR_DS_2((float) 0.0f, (float) 1 / 6, (float) 1 / 10, ImplementationGroups.IG2, Categories.PR_DS),
    PR_DS_PR_DS_3((float) 1 / 2, (float) 1 / 6, (float) 1 / 10, ImplementationGroups.IG1, Categories.PR_DS),
    PR_DS_PR_DS_4((float) 0.0f, (float) 0.0f, (float) 1 / 10, ImplementationGroups.IG3, Categories.PR_DS),
    PR_DS_PR_DS_5((float) 0.0f, (float) 0.0f, (float) 1 / 10, ImplementationGroups.IG3, Categories.PR_DS),
    PR_DS_PR_DS_6((float) 0.0f, (float) 1 / 6, (float) 1 / 10, ImplementationGroups.IG2, Categories.PR_DS),
    PR_DS_PR_DS_7((float) 0.0f, (float) 1 / 6, (float) 1 / 10, ImplementationGroups.IG2, Categories.PR_DS),
    PR_DS_PR_DS_8((float) 0.0f, (float) 0.0f, (float) 1 / 10, ImplementationGroups.IG3, Categories.PR_DS),
    PR_IP_9D_3((float) 0.0f, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG2, Categories.PR_IP),
    PR_IP_9D_5((float) 0.0f, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG2, Categories.PR_IP),
    PR_IP_9D_8((float) 0.0f, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG2, Categories.PR_IP),
    PR_IP_9D_9((float) 1 / 8, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG1, Categories.PR_IP),
    PR_IP_CSC_11_1((float) 1 / 8, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG1, Categories.PR_IP),
    PR_IP_CSC_16_1((float) 0.0f, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG2, Categories.PR_IP),
    PR_IP_CSC_16_14((float) 0.0f, (float) 0.0f, (float) 1 / 24, ImplementationGroups.IG3, Categories.PR_IP),
    PR_IP_CSC_18_4((float) 0.0f, (float) 0.0f, (float) 1 / 24, ImplementationGroups.IG3, Categories.PR_IP),
    PR_IP_CSC_2_5((float) 0.0f, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG2, Categories.PR_IP),
    PR_IP_CSC_2_6((float) 0.0f, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG2, Categories.PR_IP),
    PR_IP_CSC_2_7((float) 0.0f, (float) 0.0f, (float) 1 / 24, ImplementationGroups.IG3, Categories.PR_IP),
    PR_IP_CSC_4_3((float) 1 / 8, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG1, Categories.PR_IP),
    PR_IP_PR_IP_1((float) 1 / 8, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG1, Categories.PR_IP),
    PR_IP_PR_IP_10((float) 0.0f, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG2, Categories.PR_IP),
    PR_IP_PR_IP_11((float) 1 / 8, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG1, Categories.PR_IP),
    PR_IP_PR_IP_12((float) 0.0f, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG2, Categories.PR_IP),
    PR_IP_PR_IP_2((float) 0.0f, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG2, Categories.PR_IP),
    PR_IP_PR_IP_3((float) 0.0f, (float) 0.0f, (float) 1 / 24, ImplementationGroups.IG3, Categories.PR_IP),
    PR_IP_PR_IP_4((float) 1 / 8, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG1, Categories.PR_IP),
    PR_IP_PR_IP_5((float) 0.0f, (float) 0.0f, (float) 1 / 24, ImplementationGroups.IG3, Categories.PR_IP),
    PR_IP_PR_IP_6((float) 1 / 8, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG1, Categories.PR_IP),
    PR_IP_PR_IP_7((float) 0.0f, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG2, Categories.PR_IP),
    PR_IP_PR_IP_8((float) 0.0f, (float) 0.0f, (float) 1 / 24, ImplementationGroups.IG3, Categories.PR_IP),
    PR_IP_PR_IP_9((float) 1 / 8, (float) 1 / 18, (float) 1 / 24, ImplementationGroups.IG1, Categories.PR_IP),
    PR_MA_9D_5((float) 0.0f, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG2, Categories.PR_MA),
    PR_MA_9D_9((float) 0.0f, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG2, Categories.PR_MA),
    PR_MA_CSC_12_1((float) 1 / 6, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG1, Categories.PR_MA),
    PR_MA_CSC_12_3((float) 0.0f, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG2, Categories.PR_MA),
    PR_MA_CSC_13_5((float) 0.0f, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG2, Categories.PR_MA),
    PR_MA_CSC_16_13((float) 0.0f, (float) 0.0f, (float) 1 / 17, ImplementationGroups.IG3, Categories.PR_MA),
    PR_MA_CSC_18_3((float) 0.0f, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG2, Categories.PR_MA),
    PR_MA_CSC_4_2((float) 1 / 6, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG1, Categories.PR_MA),
    PR_MA_CSC_4_6((float) 1 / 6, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG1, Categories.PR_MA),
    PR_MA_CSC_4_8((float) 0.0f, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG2, Categories.PR_MA),
    PR_MA_CSC_4_9((float) 0.0f, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG2, Categories.PR_MA),
    PR_MA_CSC_7_3((float) 1 / 6, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG1, Categories.PR_MA),
    PR_MA_CSC_8_1((float) 1 / 6, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG1, Categories.PR_MA),
    PR_MA_CSC_8_10((float) 0.0f, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG2, Categories.PR_MA),
    PR_MA_CSC_8_3((float) 1 / 6, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG1, Categories.PR_MA),
    PR_MA_CSC_8_9((float) 0.0f, (float) 1 / 15, (float) 1 / 17, ImplementationGroups.IG2, Categories.PR_MA),
    PR_MA_PR_MA_1((float) 0.0f, (float) 0.0f, (float) 1 / 17, ImplementationGroups.IG3, Categories.PR_MA),
    PR_PT_9D_4((float) 0.0f, (float) 1 / 7, (float) 1 / 11, ImplementationGroups.IG2, Categories.PR_PT),
    PR_PT_9D_7((float) 0.0f, (float) 0.0f, (float) 1 / 11, ImplementationGroups.IG3, Categories.PR_PT),
    PR_PT_CSC_4_12((float) 0.0f, (float) 0.0f, (float) 1 / 11, ImplementationGroups.IG3, Categories.PR_PT),
    PR_PT_CSC_4_4((float) 1 / 5, (float) 1 / 7, (float) 1 / 11, ImplementationGroups.IG1, Categories.PR_PT),
    PR_PT_CSC_4_5((float) 1 / 5, (float) 1 / 7, (float) 1 / 11, ImplementationGroups.IG1, Categories.PR_PT),
    PR_PT_CSC_9_5((float) 0.0f, (float) 1 / 7, (float) 1 / 11, ImplementationGroups.IG2, Categories.PR_PT),
    PR_PT_PR_PT_1((float) 1 / 5, (float) 1 / 7, (float) 1 / 11, ImplementationGroups.IG1, Categories.PR_PT),
    PR_PT_PR_PT_2((float) 1 / 5, (float) 1 / 7, (float) 1 / 11, ImplementationGroups.IG1, Categories.PR_PT),
    PR_PT_PR_PT_3((float) 0.0f, (float) 0.0f, (float) 1 / 11, ImplementationGroups.IG3, Categories.PR_PT),
    PR_PT_PR_PT_4((float) 0.0f, (float) 0.0f, (float) 1 / 11, ImplementationGroups.IG3, Categories.PR_PT),
    PR_PT_PR_PT_5((float) 1 / 5, (float) 1 / 7, (float) 1 / 11, ImplementationGroups.IG1, Categories.PR_PT),
    DE_AE_CSC_8_12((float) 0.0f, (float) 0.0f, (float) 1 / 6, ImplementationGroups.IG3, Categories.DE_AE),
    DE_AE_DE_AE_1((float) 0.0f, (float) 1 / 3, (float) 1 / 6, ImplementationGroups.IG2, Categories.DE_AE),
    DE_AE_DE_AE_2((float) 0.0f, (float) 1 / 3, (float) 1 / 6, ImplementationGroups.IG2, Categories.DE_AE),
    DE_AE_DE_AE_3((float) 1 / 1, (float) 1 / 3, (float) 1 / 6, ImplementationGroups.IG1, Categories.DE_AE),
    DE_AE_DE_AE_4((float) 0.0f, (float) 0.0f, (float) 1 / 6, ImplementationGroups.IG3, Categories.DE_AE),
    DE_AE_DE_AE_5((float) 0.0f, (float) 0.0f, (float) 1 / 6, ImplementationGroups.IG3, Categories.DE_AE),
    DE_CM_CSC_13_1((float) 0.0f, (float) 1 / 6, (float) 1 / 11, ImplementationGroups.IG2, Categories.DE_CM),
    DE_CM_CSC_13_5((float) 0.0f, (float) 1 / 6, (float) 1 / 11, ImplementationGroups.IG2, Categories.DE_CM),
    DE_CM_CSC_3_14((float) 0.0f, (float) 0.0f, (float) 1 / 11, ImplementationGroups.IG3, Categories.DE_CM),
    DE_CM_DE_CM_1((float) 0.0f, (float) 1 / 6, (float) 1 / 11, ImplementationGroups.IG2, Categories.DE_CM),
    DE_CM_DE_CM_2((float) 0.0f, (float) 0.0f, (float) 1 / 11, ImplementationGroups.IG3, Categories.DE_CM),
    DE_CM_DE_CM_3((float) 0.0f, (float) 0.0f, (float) 1 / 11, ImplementationGroups.IG3, Categories.DE_CM),
    DE_CM_DE_CM_4((float) 1 / 2, (float) 1 / 6, (float) 1 / 11, ImplementationGroups.IG1, Categories.DE_CM),
    DE_CM_DE_CM_5((float) 0.0f, (float) 0.0f, (float) 1 / 11, ImplementationGroups.IG3, Categories.DE_CM),
    DE_CM_DE_CM_6((float) 0.0f, (float) 0.0f, (float) 1 / 11, ImplementationGroups.IG3, Categories.DE_CM),
    DE_CM_DE_CM_7((float) 1 / 2, (float) 1 / 6, (float) 1 / 11, ImplementationGroups.IG1, Categories.DE_CM),
    DE_CM_DE_CM_8((float) 0.0f, (float) 1 / 6, (float) 1 / 11, ImplementationGroups.IG2, Categories.DE_CM),
    DE_DP_CSC_17_1((float) 1 / 1, (float) 1 / 3, (float) 1 / 6, ImplementationGroups.IG1, Categories.DE_DP),
    DE_DP_CSC_17_4((float) 0.0f, (float) 1 / 3, (float) 1 / 6, ImplementationGroups.IG2, Categories.DE_DP),
    DE_DP_CSC_17_5((float) 0.0f, (float) 1 / 3, (float) 1 / 6, ImplementationGroups.IG2, Categories.DE_DP),
    DE_DP_DE_DP_2((float) 0.0f, (float) 0.0f, (float) 1 / 6, ImplementationGroups.IG3, Categories.DE_DP),
    DE_DP_DE_DP_3((float) 0.0f, (float) 0.0f, (float) 1 / 6, ImplementationGroups.IG3, Categories.DE_DP),
    DE_DP_DE_DP_5((float) 0.0f, (float) 0.0f, (float) 1 / 6, ImplementationGroups.IG3, Categories.DE_DP),
    RS_AN_CSC_17_9((float) 0.0f, (float) 0.0f, (float) 1 / 5, ImplementationGroups.IG3, Categories.RS_AN),
    RS_AN_RS_AN_1((float) 0.0f, (float) 1 / 2, (float) 1 / 5, ImplementationGroups.IG2, Categories.RS_AN),
    RS_AN_RS_AN_2((float) 0.0f, (float) 0.0f, (float) 1 / 5, ImplementationGroups.IG3, Categories.RS_AN),
    RS_AN_RS_AN_3((float) 0.0f, (float) 0.0f, (float) 1 / 5, ImplementationGroups.IG3, Categories.RS_AN),
    RS_AN_RS_AN_5((float) 0.0f, (float) 1 / 2, (float) 1 / 5, ImplementationGroups.IG2, Categories.RS_AN),
    RS_CO_CSC_17_4((float) 1 / 1, (float) 1 / 2, (float) 1 / 3, ImplementationGroups.IG1, Categories.RS_CO),
    RS_CO_CSC_17_5((float) 0.0f, (float) 1 / 2, (float) 1 / 3, ImplementationGroups.IG2, Categories.RS_CO),
    RS_CO_RS_CO_5((float) 0.0f, (float) 0.0f, (float) 1 / 3, ImplementationGroups.IG3, Categories.RS_CO),
    RS_IM_RS_IM_1((float) 0.0f, (float) 1 / 2, (float) 1 / 2, ImplementationGroups.IG2, Categories.RS_IM),
    RS_IM_RS_IM_2((float) 0.0f, (float) 1 / 2, (float) 1 / 2, ImplementationGroups.IG2, Categories.RS_IM),
    RS_MI_CSC_1_2((float) 1 / 1, (float) 1 / 3, (float) 1 / 6, ImplementationGroups.IG1, Categories.RS_MI),
    RS_MI_CSC_4_10((float) 0.0f, (float) 1 / 3, (float) 1 / 6, ImplementationGroups.IG2, Categories.RS_MI),
    RS_MI_CSC_7_7((float) 0.0f, (float) 1 / 3, (float) 1 / 6, ImplementationGroups.IG2, Categories.RS_MI),
    RS_MI_RS_MI_1((float) 0.0f, (float) 0.0f, (float) 1 / 6, ImplementationGroups.IG3, Categories.RS_MI),
    RS_MI_RS_MI_2((float) 0.0f, (float) 0.0f, (float) 1 / 6, ImplementationGroups.IG3, Categories.RS_MI),
    RS_MI_RS_MI_3((float) 0.0f, (float) 0.0f, (float) 1 / 6, ImplementationGroups.IG3, Categories.RS_MI),
    RS_RP_CSC_17_6((float) 0.0f, (float) 1 / 1, (float) 1 / 2, ImplementationGroups.IG2, Categories.RS_RP),
    RS_RP_RS_RP_1((float) 0.0f, (float) 0.0f, (float) 1 / 2, ImplementationGroups.IG3, Categories.RS_RP),
    RC_CO_RC_CO_1((float) 0.0f, (float) 0.0f, (float) 1 / 3, ImplementationGroups.IG3, Categories.RC_CO),
    RC_CO_RC_CO_2((float) 0.0f, (float) 0.0f, (float) 1 / 3, ImplementationGroups.IG3, Categories.RC_CO),
    RC_CO_RC_CO_3((float) 0.0f, (float) 0.0f, (float) 1 / 3, ImplementationGroups.IG3, Categories.RC_CO),
    RC_IM_RC_IM_1((float) 0.0f, (float) 0.0f, (float) 1 / 2, ImplementationGroups.IG3, Categories.RC_IM),
    RC_IM_RC_IM_2((float) 0.0f, (float) 0.0f, (float) 1 / 2, ImplementationGroups.IG3, Categories.RC_IM),
    RC_RP_RC_RP_1((float) 0.0f, (float) 0.0f, (float) 1 / 1, ImplementationGroups.IG3, Categories.RC_RP);

    private final float weights[] = new float[3];
    private Categories category = Categories.DE_AE;
    private ImplementationGroups minImplementationGroup = ImplementationGroups.IG3;

    /**
     * This is the constructor of the class. it creates the enum and assigns the
     * corresponding values.
     *
     * @author Manuel Domínguez-Dorado
     * @param weightIG1 A float value representing the weight of this
     * gene/expected outcome when applying implementation group 1. A number
     * between 0.0 and 1.0.
     * @param weightIG2 A float value representing the weight of this
     * gene/expected outcome when applying implementation group 2. A number
     * between 0.0 and 1.0.
     * @param weightIG3 A float value representing the weight of this
     * gene/expected outcome when applying implementation group 3. A number
     * between 0.0 and 1.0.
     * @param minImplementationGroup The minimum implementation group the
     * gene/expected outcome applies for. The gene/expected outcome will apply
     * this implementation group and onwards.
     * @param category The cybersecurity category the gene/expected outcome
     * belongs to.
     */
    private Genes(float weightIG1, float weightIG2, float weightIG3, ImplementationGroups minImplementationGroup, Categories category) {
        this.weights[ImplementationGroups.IG1.getImplementationGroupIndex()] = weightIG1;
        this.weights[ImplementationGroups.IG2.getImplementationGroupIndex()] = weightIG2;
        this.weights[ImplementationGroups.IG3.getImplementationGroupIndex()] = weightIG3;
        this.minImplementationGroup = minImplementationGroup;
        this.category = category;
    }

    /**
     * Thies method returns the weight of the gene/expected outcome taking into
     * consideration the impleentation group that applies.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The implementation grou that applies.
     * @return the weight of the gene/expected outcome taking into consideration
     * the impleentation group that applies.
     */
    public float getWeight(ImplementationGroups implementationGroup) {
        return this.weights[implementationGroup.getImplementationGroupIndex()];
    }

    /**
     * Given an implementation group, this method returns whether the
     * gene/expected outcome applies for it or not.
     *
     * @author Manuel Domínguez-Dorado
     * @param implementationGroup The applicable implementation group.
     * @return true, if the gene/expected outcome applies. Otherwise,
     * false.
     */
    public boolean appliesToIG(ImplementationGroups implementationGroup) {
        return (this.minImplementationGroup.getImplementationGroupIndex() <= implementationGroup.getImplementationGroupIndex());
    }

    /**
     * This method returns the cybersecurity category the gene/expected outcome
     * belongs to.
     *
     * @author Manuel Domínguez-Dorado
     * @return the cybersecurity category the gene/expected outcome belongs to.
     */
    public Categories getCategory() {
        return this.category;
    }

    /**
     * This method returns the list of genes that belongs to a given
     * cybersecurity category and are applicable for a given implementation
     * group.
     *
     * @author Manuel Domínguez-Dorado
     * @param category The cybersecurity category whose genes/expected outcomes
     * are required.
     * @param implementationGroup The applicable implementation group.
     * @return the list of genes that belongs to a given cybersecurity category
     * and are applicable for a given implementation group.
     */
    public static CopyOnWriteArrayList<Genes> getGenesFor(Categories category, ImplementationGroups implementationGroup) {
        CopyOnWriteArrayList<Genes> genesList = new CopyOnWriteArrayList<>();
        for (Genes gene : Genes.values()) {
            if (gene.appliesToIG(implementationGroup) && (gene.getCategory() == category)) {
                genesList.add(gene);
            }
        }
        return genesList;
    }
}
