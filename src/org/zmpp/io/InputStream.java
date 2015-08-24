/*
 * $Id: InputStream.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 10/07/2005
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
package org.zmpp.io;

/**
 * This interface defines a Z-machine input stream.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface InputStream {

    /**
     * Cancels a wait on the input stream.
     */
    void cancelInput();

    /**
     * Reads only one ZSCII character from the stream. This is somewhat
     * immediate.
     *
     * @param flushBeforeGet flushes the stream before the get operation
     * @return the ZSCII character
     */
    char getZsciiChar(boolean flushBeforeGet);

    /**
     * Release underlying resources.
     */
    void close();
}
