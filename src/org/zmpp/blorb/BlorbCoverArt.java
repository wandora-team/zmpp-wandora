/*
 * $Id: BlorbCoverArt.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/03/04
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

import org.zmpp.iff.Chunk;
import org.zmpp.iff.FormChunk;

/**
 * This class extracts the Frontispiece chunk.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class BlorbCoverArt {

    private int coverartnum;

    /**
     * Constructor.
     *
     * @param formchunk the form chunk
     */
    public BlorbCoverArt(FormChunk formchunk) {

        readFrontispiece(formchunk);
    }

    private void readFrontispiece(final FormChunk formchunk) {

        //System.out.println("SEARCHIN FRONTISPIECE");
        final Chunk fspcchunk = formchunk.getSubChunk("Fspc".getBytes());
        if (fspcchunk != null) {
            //System.out.println("FOUND FRONTISPIECE");
            coverartnum = (int) fspcchunk.getMemory().readUnsigned32(Chunk.CHUNK_HEADER_LENGTH);
        }
    }

    /**
     * Returns the number of the cover art.
     *
     * @return the cover art
     */
    public int getCoverArtNum() {
        return coverartnum;
    }
}
