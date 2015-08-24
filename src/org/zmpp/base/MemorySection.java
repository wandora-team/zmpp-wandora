/*
 * $Id: MemorySection.java 536 2008-02-19 06:03:27Z weiju $
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
package org.zmpp.base;

/**
 * A MemorySection object wraps a Memory object, a length and a start to support
 * subsections within memory. All access functions will be relative to the
 * initialized start offset within the global memory.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class MemorySection implements Memory {

    private Memory memory;
    private int start;
    private int length;

    /**
     * Constructor.
     *
     * @param memory the Memory objeci to wrap
     * @param start the start of the section
     * @param length the length of the section
     */
    public MemorySection(final Memory memory, final int start, final int length) {
        super();
        this.memory = memory;
        this.start = start;
        this.length = length;
    }

    /**
     * Returns the length of this object in bytes.
     *
     * @return the length in bytes
     */
    public int getLength() {
        return length;
    }

    /**
     * {@inheritDoc}
     */
    public long readUnsigned48(final int address) {
        return memory.readUnsigned48(address + start);
    }

    /**
     * {@inheritDoc}
     */
    public void writeUnsigned48(final int address, final long value) {
        memory.writeUnsigned48(address + start, value);
    }

    /**
     * {@inheritDoc}
     */
    public void writeUnsignedShort(final int address, final int value) {
        memory.writeUnsignedShort(address + start, value);
    }

    /**
     * {@inheritDoc}
     */
    public void writeShort(final int address, final short value) {
        memory.writeShort(address + start, value);
    }

    /**
     * {@inheritDoc}
     */
    public void writeUnsignedByte(final int address, final short value) {
        memory.writeUnsignedByte(address + start, value);
    }

    /**
     * {@inheritDoc}
     */
    public void writeByte(final int address, final byte value) {
        memory.writeByte(address + start, value);
    }

    /**
     * {@inheritDoc}
     */
    public void writeUnsigned32(final int address, final long value) {
        memory.writeUnsigned32(address + start, value);
    }

    /**
     * {@inheritDoc}
     */
    public long readUnsigned32(final int address) {
        return memory.readUnsigned32(address + start);
    }

    /**
     * {@inheritDoc}
     */
    public int readUnsignedShort(final int address) {
        return memory.readUnsignedShort(address + start);
    }

    /**
     * {@inheritDoc}
     */
    public short readShort(final int address) {
        return memory.readShort(address + start);
    }

    /**
     * {@inheritDoc}
     */
    public short readUnsignedByte(final int address) {
        return memory.readUnsignedByte(address + start);
    }

    /**
     * {@inheritDoc}
     */
    public byte readByte(final int address) {
        return memory.readByte(address + start);
    }
}
