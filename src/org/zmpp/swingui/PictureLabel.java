/*
 * $Id: PictureLabel.java 535 2008-02-19 06:02:50Z weiju $
 * 
 * Created on 2006/03/10
 * Copyright 2005-2008 by Wei-ju Wu
 * This file is part of The Z-machine Preservation Project (ZMPP).
 *
 * ZMPP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ZMPP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ZMPP.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.zmpp.swingui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

/**
 * This class implements a scalable image label. It preserves the aspect of the
 * displayed picture. This label class is used to display cover art.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class PictureLabel extends JLabel {

    /**
     * Label is serializable.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The image.
     */
    private BufferedImage image;

    /**
     * Constructor.
     *
     * @param image a BufferedImage
     */
    public PictureLabel(BufferedImage image) {

        this.image = image;
    }

    /**
     * {@inheritDoc}
     */
    public void paint(Graphics g) {

        // To preserve the aspect, scale from the largest side
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        double scalefactor = (imageHeight > imageWidth)
                ? (double) getHeight() / (double) imageHeight
                : (double) getWidth() / (double) imageWidth;

        g.drawImage(image, 0, 0,
                (int) (imageWidth * scalefactor),
                (int) (imageHeight * scalefactor), this);
    }
}
