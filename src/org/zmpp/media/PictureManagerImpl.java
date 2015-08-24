/*
 * $Id: PictureManagerImpl.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/02/22
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
package org.zmpp.media;

import java.awt.Dimension;

import org.zmpp.blorb.BlorbImage;
import org.zmpp.vm.Machine;

public class PictureManagerImpl implements PictureManager {

    private int release;
    private MediaCollection<BlorbImage> pictures;
    private Machine machine;

    public PictureManagerImpl(int release, Machine machine,
            MediaCollection<BlorbImage> pictures) {

        this.release = release;
        this.pictures = pictures;
        this.machine = machine;
    }

    /**
     * {@inheritDoc}
     */
    public Dimension getPictureSize(final int picturenum) {

        final BlorbImage img = pictures.getResource(picturenum);
        if (img != null) {

            return img.getSize(machine.getScreen6().getWidth(),
                    machine.getScreen6().getHeight());
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public BlorbImage getPicture(final int picturenum) {

        return pictures.getResource(picturenum);
    }

    /**
     * {@inheritDoc}
     */
    public int getNumPictures() {

        return pictures.getNumResources();
    }

    /**
     * {@inheritDoc}
     */
    public void preload(final int[] picnumbers) {

        // no preloading at the moment
    }

    /**
     * {@inheritDoc}
     */
    public int getRelease() {

        return release;
    }

    /**
     * {@inheritDoc}
     */
    public void reset() {

        // no resetting supported
    }
}
