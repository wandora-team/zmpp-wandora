/*
 * $Id: Abbreviations.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2005/09/25
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
import org.zmpp.encoding.ZCharDecoder.AbbreviationsTable;

/**
 * This class represents a view to the abbreviations table. The table starts at
 * the predefined address within the header and contains pointers to ZSCII
 * strings in the memory map. These pointers are word addresses as opposed to
 * all other addresses in the memory map, therefore the actual value has to
 * multiplied by two to get the real address.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class Abbreviations implements AbbreviationsTable {

    /**
     * The memory object.
     */
    private Memory memory;

    /**
     * The start address of the abbreviations table.
     */
    private int address;

    /**
     * Constructor.
     *
     * @param memory the memory map
     * @param address the start address of the abbreviations table
     */
    public Abbreviations(final Memory memory, final int address) {
        super();
        this.memory = memory;
        this.address = address;
    }

    /**
     * The abbreviation table contains word addresses, so read out the pointer
     * and multiply by two
     *
     * @param entryNum the entry index in the abbreviations table
     * @return the word address
     */
    public int getWordAddress(final int entryNum) {
        return memory.readUnsignedShort(address + entryNum * 2) * 2;
    }
}
