/*
 * $Id: FormChunk.java 536 2008-02-19 06:03:27Z weiju $
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

import java.util.Iterator;

/**
 * FormChunk is the wrapper chunk for all other chunks.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface FormChunk extends Chunk {

    /**
     * Returns the sub id.
     *
     * @return the sub id
     */
    byte[] getSubId();

    /**
     * Returns an iterator of chunks contained in this form chunk.
     *
     * @return the enumeration of sub chunks
     */
    Iterator<Chunk> getSubChunks();

    /**
     * Returns the chunk with the specified id.
     *
     * @param id the id
     * @return the chunk with the specified id or null if it does not exist
     */
    Chunk getSubChunk(byte[] id);

    /**
     * Returns the sub chunk at the specified address or null if it does not
     * exist.
     *
     * @param address the address of the chunk
     * @return the chunk or null if it does not exist
     */
    Chunk getSubChunk(int address);
}
