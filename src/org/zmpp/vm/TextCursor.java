/*
 * $Id: TextCursor.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 11/22/2005
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

/**
 * This defines the operations that can be performed on a text cursor.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface TextCursor {

    /**
     * Returns the current line.
     *
     * @return the current line
     */
    int getLine();

    /**
     * Returns the current column.
     *
     * @return the current column
     */
    int getColumn();

    /**
     * Sets the current line. A value <= 0 will set the line to 1.
     *
     * @param line the new current line
     */
    void setLine(int line);

    /**
     * Sets the current column. A value <= 0 will set the column to 1.
     *
     * @param column the new current column
     */
    void setColumn(int column);

    /**
     * Sets the new position. Values <= 0 will set the corresponding values to
     * 1.
     *
     * @param line the new line
     * @param column the new column
     */
    void setPosition(int line, int column);
}
