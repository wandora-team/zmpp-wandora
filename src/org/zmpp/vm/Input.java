/*
 * $Id: Input.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/02/14
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

import org.zmpp.io.InputStream;

public interface Input {

    /**
     * The input stream number for the keyboard.
     */
    final static int INPUTSTREAM_KEYBOARD = 0;

    /**
     * The input stream number for file input.
     */
    final static int INPUTSTREAM_FILE = 1;

    void close();

    /**
     * Sets an input stream to the specified number.
     *
     * @param streamnumber the input stream number
     * @param stream the input stream to set
     */
    void setInputStream(int streamnumber, InputStream stream);

    /**
     * Selects an input stream.
     *
     * @param streamnumber the input stream number to select
     */
    void selectInputStream(int streamnumber);

    /**
     * Returns the selected input stream.
     *
     * @return the selected input stream
     */
    InputStream getSelectedInputStream();

}
