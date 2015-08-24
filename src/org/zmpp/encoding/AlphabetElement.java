/*
 * $Id: AlphabetElement.java 536 2008-02-19 06:03:27Z weiju $
 *
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
package org.zmpp.encoding;

import org.zmpp.encoding.AlphabetTable.Alphabet;

/**
 * This class represents an alphabet element which is an alphabet and an index
 * to that alphabet. We need this to determine what kind of encoding we need.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class AlphabetElement {

    /**
     * The zchar code or the ZSCII code, if alphabet is null.
     */
    private short zcharCode;

    /**
     * The alphabet or null, if index is a ZSCII code.
     */
    private Alphabet alphabet;

    /**
     * Constructor.
     *
     * @param alphabet the alphabet (can be null)
     * @param zcharCode the zcharCode in the alphabet or the ZSCII code
     */
    public AlphabetElement(Alphabet alphabet, short zcharCode) {

        this.alphabet = alphabet;
        this.zcharCode = zcharCode;
    }

    /**
     * Returns the alphabet. Can be null, in that case index represents the
     * ZSCII code.
     *
     * @return the alphabet
     */
    public Alphabet getAlphabet() {
        return alphabet;
    }

    /**
     * Returns the index to the table. If the alphabet is null, this is the
     * plain ZSCII code and should be turned into a 10-bit code by the encoder.
     *
     * @return the z char code in the specified alphabet or the ZSCII code
     */
    public short getZCharCode() {
        return zcharCode;
    }
}
