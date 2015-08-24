/*
 * $Id: Chunk.java 536 2008-02-19 06:03:27Z weiju $
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

import org.zmpp.base.Memory;

/**
 * The basic data structure for an IFF file, a chunk.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface Chunk {

    /**
     * The length of an IFF chunk id in bytes.
     */
    static final int CHUNK_ID_LENGTH = 4;

    /**
     * The length of an IFF chunk size word in bytes.
     */
    static final int CHUNK_SIZEWORD_LENGTH = 4;

    /**
     * The chunk header size.
     */
    static final int CHUNK_HEADER_LENGTH = CHUNK_ID_LENGTH
            + CHUNK_SIZEWORD_LENGTH;

    /**
     * Returns this IFF chunk's id. An id is a 4 byte array.
     *
     * @return the id
     */
    byte[] getId();

    /**
     * The chunk data size, excluding id and size word.
     *
     * @return the size
     */
    int getSize();

    /**
     * Returns true if this is a valid chunk.
     *
     * @return true if valid, false otherwise
     */
    boolean isValid();

    /**
     * Returns a memory object to access the chunk.
     *
     * @return the Memory object
     */
    Memory getMemory();

    /**
     * Returns the address of the chunk within the global FORM chunk.
     *
     * @return the address within the form chunk
     */
    int getAddress();
}
