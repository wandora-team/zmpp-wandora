/*
 * $Id: ZsciiStringBuilder.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/02/01
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

import java.util.ArrayList;
import java.util.List;

/**
 * This class works similar to the StringBuilder class in that it is not
 * synchronized and supports appending characters.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class ZsciiStringBuilder {

    /**
     * This list holds the data.
     */
    private List<Character> data = new ArrayList<Character>();

    /**
     * Adds a ZSCII character to the builder object.
     *
     * @param zsciiChar the ZSCII char to add
     */
    public void append(final char zsciiChar) {
        data.add(zsciiChar);
    }

    /**
     * Appends the characters of the specified ZSCII string to this builder
     * object.
     *
     * @param str a ZSCII string
     */
    public void append(final ZsciiString str) {
        for (int i = 0, n = str.length(); i < n; i++) {
            append(str.charAt(i));
        }
    }

    /**
     * Retrieves a ZSCII string.
     *
     * @return the ZSCII string
     */
    public ZsciiString toZsciiString() {
        final char[] strdata = new char[data.size()];
        for (int i = 0; i < strdata.length; i++) {
            strdata[i] = data.get(i);
        }
        return new ZsciiString(strdata);
    }

    /**
     * Returns the current length of the buffer.
     *
     * @return the length
     */
    public int length() {
        return data.size();
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return toZsciiString().toString();
    }
}
