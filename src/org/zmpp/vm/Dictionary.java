/*
 * $Id: Dictionary.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 09/24/2005
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

import org.zmpp.encoding.ZsciiString;

/**
 * This is the interface definition for a dictionary.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface Dictionary {

    /**
     * Returns the number of separators.
     *
     * @return the number of separators
     */
    int getNumberOfSeparators();

    /**
     * Returns the separator at position i as a ZSCII character.
     *
     * @param i the separator number, zero-based
     * @return the separator
     */
    byte getSeparator(int i);

    /**
     * Returns the length of a dictionary entry.
     *
     * @return the entry length
     */
    int getEntryLength();

    /**
     * Returns the number of dictionary entries.
     *
     * @return the number of entries
     */
    int getNumberOfEntries();

    /**
     * Returns the entry address at the specified position.
     *
     * @param entryNum entry number between (0 - getNumberOfEntries() - 1)
     * @return the entry address
     */
    int getEntryAddress(int entryNum);

    /**
     * Looks up a string in the dictionary. The word will be truncated to the
     * maximum word length and looked up. The result is the address of the entry
     * or 0 if it is not found.
     *
     * @param token a token
     * @return the address of the token or 0
     */
    int lookup(ZsciiString token);
}
