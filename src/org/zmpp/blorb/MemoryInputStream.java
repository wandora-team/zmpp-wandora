/*
 * $Id: MemoryAccessInputStream.java 520 2007-11-13 19:14:51Z weiju $
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

import java.io.IOException;
import java.io.InputStream;

import org.zmpp.base.Memory;

/**
 * This class encapsulates the a memory object within an input stream.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class MemoryInputStream extends InputStream {

    /**
     * The memory object this stream is based on.
     */
    private Memory memory;

    /**
     * The position in the stream.
     */
    private int position;

    /**
     * Supports a mark.
     */
    private int mark;

    /**
     * The size of the memory.
     */
    private int size;

    /**
     * Constructor.
     *
     * @param memory a memory object
     * @param offset the byte offset
     * @param size the memory size
     */
    public MemoryInputStream(final Memory memory,
            final int offset, final int size) {
        super();
        this.memory = memory;
        position += offset;
        this.size = size;
    }

    /**
     * {@inheritDoc}
     */
    public int read() throws IOException {
        if (position >= size) {
            return -1;
        }
        return memory.readUnsignedByte(position++);
    }

    /**
     * {@inheritDoc}
     */
    public void mark(final int readLimit) {
        mark = position;
    }

    /**
     * {@inheritDoc}
     */
    public void reset() {
        position = mark;
    }
}
