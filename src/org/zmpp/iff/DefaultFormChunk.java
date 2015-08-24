/*
 * $Id: DefaultFormChunk.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2005/09/23
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
package org.zmpp.iff;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.zmpp.base.Memory;
import org.zmpp.base.MemorySection;

/**
 * This class implements the FormChunk interface.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class DefaultFormChunk extends DefaultChunk implements FormChunk {

    /**
     * The sub type id.
     */
    private byte[] subId;

    /**
     * The list of sub chunks.
     */
    private List<Chunk> subChunks;

    /**
     * Constructor.
     *
     * @param memory a MemoryAccess object
     */
    public DefaultFormChunk(final Memory memory) throws IOException {
        super(memory, 0);
        initBaseInfo();
        readSubChunks();
    }

    /**
     * Initialize the id field.
     */
    private void initBaseInfo() throws IOException {
        if (!"FORM".equals(new String(getId()))) {
            throw new IOException("not a valid IFF format");
        }
        // Determine the sub id
        subId = new byte[CHUNK_ID_LENGTH];
        final int offset = CHUNK_HEADER_LENGTH;

        for (int i = 0; i < 4; i++) {
            subId[i] = memory.readByte(i + offset);
        }
    }

    /**
     * Read this form chunk's sub chunks.
     */
    private void readSubChunks() {
        subChunks = new ArrayList<Chunk>();

        // skip the identifying information
        final int length = getSize();
        int offset = CHUNK_HEADER_LENGTH + CHUNK_ID_LENGTH;
        int chunkTotalSize = 0;

        while (offset < length) {
            final Memory memarray = new MemorySection(memory, offset,
                    length - offset);
            final Chunk subchunk = new DefaultChunk(memarray, offset);
            subChunks.add(subchunk);
            chunkTotalSize = subchunk.getSize() + CHUNK_HEADER_LENGTH;

            // Determine if padding is necessary
            chunkTotalSize = (chunkTotalSize % 2) == 0 ? chunkTotalSize
                    : chunkTotalSize + 1;
            offset += chunkTotalSize;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid() {
        return (new String(getId())).equals("FORM");
    }

    /**
     * {@inheritDoc}
     */
    public byte[] getSubId() {
        return subId;
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<Chunk> getSubChunks() {
        return subChunks.iterator();
    }

    /**
     * {@inheritDoc}
     */
    public Chunk getSubChunk(final byte[] id) {
        for (Chunk chunk : subChunks) {

            if (Arrays.equals(id, chunk.getId())) {

                return chunk;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public Chunk getSubChunk(final int address) {
        for (Chunk chunk : subChunks) {

            if (chunk.getAddress() == address) {

                return chunk;
            }
        }
        return null;
    }
}
