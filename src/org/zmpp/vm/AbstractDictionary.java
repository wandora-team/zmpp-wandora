/*
 * $Id: AbstractDictionary.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/09/25
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
package org.zmpp.vm;

import org.zmpp.base.Memory;
import org.zmpp.encoding.ZCharDecoder;
import org.zmpp.encoding.ZsciiString;

public abstract class AbstractDictionary implements Dictionary {

    /**
     * The memory map.
     */
    private Memory memory;

    /**
     * The dictionary start address.
     */
    private int address;

    /**
     * A Z char decoder.
     */
    private ZCharDecoder decoder;

    /**
     * A sizes object.
     */
    private DictionarySizes sizes;

    /**
     * Constructor.
     *
     * @param memory the memory object
     * @param address the start address of the dictionary
     * @param converter a Z char decoder object
     * @param an object specifying the sizes of the dictionary entries
     */
    public AbstractDictionary(final Memory memory, final int address,
            final ZCharDecoder decoder,
            final DictionarySizes sizes) {

        super();
        this.memory = memory;
        this.address = address;
        this.decoder = decoder;
        this.sizes = sizes;
    }

    /**
     * {@inheritDoc}
     */
    public int getNumberOfSeparators() {
        return memory.readUnsignedByte(address);
    }

    /**
     * {@inheritDoc}
     */
    public byte getSeparator(final int i) {
        return (byte) memory.readUnsignedByte(address + i + 1);
    }

    /**
     * {@inheritDoc}
     */
    public int getEntryLength() {
        return memory.readUnsignedByte(address + getNumberOfSeparators() + 1);
    }

    /**
     * {@inheritDoc}
     */
    public int getNumberOfEntries() {
    // The number of entries is a signed value so that we can recognize
        // a negative number
        return memory.readShort(address + getNumberOfSeparators() + 2);
    }

    /**
     * {@inheritDoc}
     */
    public int getEntryAddress(final int entryNum) {
        final int headerSize = getNumberOfSeparators() + 4;
        return address + headerSize + entryNum * getEntryLength();
    }

    protected ZCharDecoder getDecoder() {
        return decoder;
    }

    protected Memory getMemory() {
        return memory;
    }

    protected DictionarySizes getSizes() {
        return sizes;
    }

    protected ZsciiString truncateToken(final ZsciiString token) {
    // Unfortunately it seems that the maximum size of an entry is not equal 
        // to the size declared in the dictionary header, therefore we take
        // the maximum length of a token defined in the Z-machine specification.    
        // The lookup token can only be 6 characters long in version 3
        // and 9 in versions >= 4
        if (token.length() > sizes.getMaxEntryChars()) {
            return token.substring(0, sizes.getMaxEntryChars());
        }
        return token;
    }

    /**
     * Creates a string presentation of this dictionary.
     *
     * @return the string presentation
     */
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        int entryAddress;
        int i = 0;
        final int n = getNumberOfEntries();

        while (true) {

            entryAddress = getEntryAddress(i);
            final String str = getDecoder().decode2Zscii(getMemory(),
                    entryAddress, sizes.getNumEntryBytes()).toString();
            buffer.append(String.format("[%4d] '%-9s' ", (i + 1), str));
            i++;
            if ((i % 4) == 0) {
                buffer.append("\n");
            }
            if (i == n) {
                break;
            }
        }
        return buffer.toString();
    }
}
