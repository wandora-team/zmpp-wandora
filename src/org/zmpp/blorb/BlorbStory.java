/*
 * $Id: BlorbStory.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/03/03
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
 * This class extracts story data from a Blorb file.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class BlorbStory {

    private byte[] storydata;

    public BlorbStory(final FormChunk formchunk) {

        super();
        storydata = readStoryFromZBlorb(formchunk);
    }

    public byte[] getStoryData() {
        return storydata;
    }

    private byte[] readStoryFromZBlorb(final FormChunk formchunk) {

        final Chunk chunk = formchunk.getSubChunk("ZCOD".getBytes());
        final int size = chunk.getSize();
        final byte[] data = new byte[size];
        for (int i = 0; i < size; i++) {

            data[i] = chunk.getMemory().readByte(i + Chunk.CHUNK_HEADER_LENGTH);
        }
        return data;
    }
}
