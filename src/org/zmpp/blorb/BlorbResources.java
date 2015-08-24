/*
 * $Id: BlorbResources.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/02/06
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
package org.zmpp.blorb;

import org.zmpp.iff.FormChunk;
import org.zmpp.media.InformMetadata;
import org.zmpp.media.MediaCollection;
import org.zmpp.media.Resources;
import org.zmpp.media.SoundEffect;

/**
 * This class encapsulates a Blorb file and offers access to the sound and
 * graphics media collections.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class BlorbResources implements Resources {

    /**
     * The file's images.
     */
    private MediaCollection<BlorbImage> images;

    /**
     * The file's sounds.
     */
    private MediaCollection<SoundEffect> sounds;

    /**
     * The cover art.
     */
    private BlorbCoverArt coverart;

    /**
     * The meta data.
     */
    private BlorbMetadataHandler metadata;

    /**
     * The release number.
     */
    private int release;

    /**
     * Constructor.
     *
     * @param formchunk a form chunk in Blorb format
     */
    public BlorbResources(FormChunk formchunk) {

        images = new BlorbImages(formchunk);
        sounds = new BlorbSounds(formchunk);
        coverart = new BlorbCoverArt(formchunk);
        metadata = new BlorbMetadataHandler(formchunk);
    }

    /**
     * {@inheritDoc}
     */
    public MediaCollection<BlorbImage> getImages() {
        return images;
    }

    /**
     * {@inheritDoc}
     */
    public MediaCollection<SoundEffect> getSounds() {
        return sounds;
    }

    /**
     * {@inheritDoc}
     */
    public int getCoverArtNum() {
        return coverart.getCoverArtNum();
    }

    /**
     * {@inheritDoc}
     */
    public InformMetadata getMetadata() {
        return metadata.getMetadata();
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
    public boolean hasInfo() {
        return metadata.getMetadata() != null;
    }
}
