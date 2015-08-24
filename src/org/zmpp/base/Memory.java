/*
 * $Id: MemoryAccess.java 520 2007-11-13 19:14:51Z weiju $
 * 
 * Copyright 2005-2008 Wei-ju Wu
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
 * This class manages read and write access to the byte array which contains the
 * story file data. It is the only means to read and manipulate the memory map.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface Memory {

  // ************************************************************************
    // ****
    // **** Read access
    // ****
    // *****************************
    /**
     * Reads the 48-bit unsigned word at the specified address. From the
     * specified address, 6 bytes are read and or'ed together.
     *
     * @param address the address to read from
     * @return the word at the specified address
     */
    long readUnsigned48(int address);

    /**
     * Reads the unsigned 32 bit word at the specified address.
     *
     * @param address the address
     * @return the 32 bit unsigned value as long
     */
    long readUnsigned32(int address);

    /**
     * Reads the unsigned 16 bit word at the specified address.
     *
     * @param address the address
     * @return the 16 bit unsigned value as int
     */
    int readUnsignedShort(int address);

    /**
     * Returns the signed 16 bit word at the specified address.
     *
     * @param address the address
     * @return the 16 bit signed value
     */
    short readShort(int address);

    /**
     * Returns the unsigned 8 bit value at the specified address.
     *
     * @param address the address
     * @return the 8 bit unsigned value
     */
    short readUnsignedByte(int address);

    /**
     * Returns the signed 8 bit value at specified address.
     *
     * @param address the byte address
     * @return the 8 bit signed value
     */
    byte readByte(int address);

  // ************************************************************************
    // ****
    // **** Write access
    // ****
    // *****************************
    /**
     * Writes an unsigned 16 bit value to the specified address.
     *
     * @param address the address to write to
     * @param value the value to write
     */
    void writeUnsignedShort(int address, int value);

    /**
     * Writes a short value to the memory.
     *
     * @param address the address
     * @param value the value
     */
    void writeShort(int address, short value);

    /**
     * Writes an unsigned byte value to the specified address.
     *
     * @param address the address to write to
     * @param value the value to write
     */
    void writeUnsignedByte(int address, short value);

    /**
     * Writes a byte value to the specified address.
     *
     * @param address the address
     * @param value the value
     */
    void writeByte(int address, byte value);

    /**
     * Writes an unsigned 32 bit value to the specified address.
     *
     * @param address the address to write to
     * @param value the value to write
     */
    void writeUnsigned32(int address, long value);

    /**
     * Writes the specified unsigned 48 bit value to the specified address. The
     * value is written in 6 consecutive bytes.
     *
     * @param address the address
     * @param value the value
     */
    void writeUnsigned48(int address, long value);
}
