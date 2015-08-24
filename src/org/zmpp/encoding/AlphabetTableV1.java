/*
 * $Id: AlphabetTableV1.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/01/17
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

/**
 * An alphabet table in V1 story files behaves like an alphabet table in V2,
 * except that it has a different A2 alphabet and does not support
 * abbreviations. Furthermore, character 1 returns '\n'. This is a thing that
 * leads to the extension of the getAnChar() methods, handling index -5.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class AlphabetTableV1 extends AlphabetTableV2 {

    /**
     * V1 Alphabet 2 has a slightly different structure.
     */
    private static final String A2CHARS = " 0123456789.,!?_#'\"/\\<-:()";

    /**
     * {@inheritDoc}
     */
    public char getA0Char(final byte zchar) {

        if (zchar == 1) {
            return '\n';
        }
        return super.getA0Char(zchar);
    }

    /**
     * {@inheritDoc}
     */
    public char getA1Char(final byte zchar) {

        if (zchar == 1) {
            return '\n';
        }
        return super.getA1Char(zchar);
    }

    /**
     * {@inheritDoc}
     */
    public char getA2Char(final byte zchar) {

        if (zchar == 0) {
            return ' ';
        }
        if (zchar == 1) {
            return '\n';
        }
        return A2CHARS.charAt(zchar - ALPHABET_START);
    }

    /**
     * {@inheritDoc}
     */
    public final byte getA2CharCode(final char zsciiChar) {
        return getCharCodeFor(A2CHARS, zsciiChar);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isAbbreviation(final char zchar) {
        return false;
    }
}
