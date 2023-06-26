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
package com.manolodominguez.fleco.gui;

import java.awt.Image;
import java.util.EnumMap;
import javax.swing.ImageIcon;

/**
 * This class implements a image broker that preloads all images needed by FLECO
 * Studio to give each GUI component the required image faster.
 *
 * @author Manuel Domínguez-Dorado
 */
public class ImageBroker {

    private static volatile ImageBroker instance;
    private final EnumMap<AvailableImages, ImageIcon> imageIcons16x16;
    private final EnumMap<AvailableImages, ImageIcon> imageIcons32x32;

    /**
     * This method is the constructor of the class. It is create a new instance
     * of ImageBroker.
     *
     * @author Manuel Domínguez-Dorado
     */
    ImageBroker() {
        this.imageIcons16x16 = new EnumMap<>(AvailableImages.class);
        this.imageIcons32x32 = new EnumMap<>(AvailableImages.class);
        try {
            for (AvailableImages availableImage : AvailableImages.values()) {
                this.imageIcons16x16.put(availableImage, new ImageIcon(getClass().getResource(availableImage.getPath16x16())));
                this.imageIcons32x32.put(availableImage, new ImageIcon(getClass().getResource(availableImage.getPath32x32())));
            }
        }
        catch (Exception e) {
            System.out.println("Error loading icons");
        }
    }

    /**
     * This method returns a instance of this class. As this class implements
     * the singleton pattern, this checks whether a new instance has to be
     * created or the existing one has to be returned.
     *
     * @author Manuel Domínguez-Dorado
     * @return An instance of ImageBroker
     */
    public static ImageBroker getInstance() {
        ImageBroker localInstance = ImageBroker.instance;
        if (localInstance == null) {
            synchronized (ImageBroker.class) {
                localInstance = ImageBroker.instance;
                if (localInstance == null) {
                    ImageBroker.instance = localInstance = new ImageBroker();
                }
            }
        }
        return localInstance;
    }

    /**
     * This method request a given image from the ImageBroker.
     *
     * @author Manuel Domínguez-Dorado
     * @param imageID The image ID the is requested.
     * @return The requested image or a default one if the requested image is
     * not found.
     */
    public Image getImage16x16(AvailableImages imageID) {
        ImageIcon imageIcon = this.imageIcons16x16.get(imageID);
        if (imageIcon == null) {
            imageIcon = this.imageIcons16x16.get(AvailableImages.NOT_FOUND);
        }
        return imageIcon.getImage();
    }

    /**
     * This method request a given image from the ImageBroker.
     *
     * @author Manuel Domínguez-Dorado
     * @param imageID The image ID the is requested.
     * @return The requested image or a default one if the requested image is
     * not found.
     */
    public Image getImage32x32(AvailableImages imageID) {
        ImageIcon imageIcon = this.imageIcons32x32.get(imageID);
        if (imageIcon == null) {
            imageIcon = this.imageIcons32x32.get(AvailableImages.NOT_FOUND);
        }
        return imageIcon.getImage();
    }

    /**
     * This method request a given image icon from the ImageBroker.
     *
     * @author Manuel Domínguez-Dorado
     * @param imageID The image ID the is requested.
     * @return The requested image icon or a default one if the requested image
     * icon is not found.
     */
    public ImageIcon getImageIcon16x16(AvailableImages imageID) {
        ImageIcon imageIcon = this.imageIcons16x16.get(imageID);
        if (imageIcon == null) {
            imageIcon = this.imageIcons16x16.get(AvailableImages.NOT_FOUND);
        }
        return imageIcon;
    }

    /**
     * This method request a given image icon from the ImageBroker.
     *
     * @author Manuel Domínguez-Dorado
     * @param imageID The image ID the is requested.
     * @return The requested image icon or a default one if the requested image
     * icon is not found.
     */
    public ImageIcon getImageIcon32x32(AvailableImages imageID) {
        ImageIcon imageIcon = this.imageIcons32x32.get(imageID);
        if (imageIcon == null) {
            imageIcon = this.imageIcons32x32.get(AvailableImages.NOT_FOUND);
        }
        return imageIcon;
    }
}
