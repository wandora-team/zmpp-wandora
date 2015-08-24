/*
 * $Id: Resources.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/02/13
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

import org.zmpp.blorb.BlorbImage;

/**
 * This interface defines access to the Z-machine's media resources.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface Resources {

    /**
     * The release number of the resource file.
     *
     * @return the release number
     */
    int getRelease();

    /**
     * Returns the images of this file.
     *
     * @return the images
     */
    MediaCollection<BlorbImage> getImages();

    /**
     * Returns the sounds of this file.
     *
     * @return the sounds
     */
    MediaCollection<SoundEffect> getSounds();

    /**
     * Returns the number of the cover art picture.
     *
     * @return the number of the cover art picture
     */
    int getCoverArtNum();

    /**
     * Returns the inform meta data if available.
     *
     * @return the meta data
     */
    InformMetadata getMetadata();

    /**
     * Returns true if the resource file has information.
     *
     * @return true if information, false, otherwise
     */
    boolean hasInfo();
}
