/*
 * $Id: InputLine.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/03/10
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

import java.util.List;

/**
 * This interface is used from CommandHistory to manipulate the input line.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface InputLine {

    /**
     * Deletes the previous character in the input line.
     *
     * @param inputbuffer the input buffer
     * @param pointer the pointer
     * @return the new pointer after delete
     */
    int deletePreviousChar(List<Character> inputbuffer, int pointer);

    /**
     * Adds a character to the current input line.
     *
     * @param inputbuffer the input buffer
     * @param textbuffer the textbuffer address
     * @param pointer the pointer address
     * @param zsciiChar the character to add
     * @return the new pointer
     */
    int addChar(List<Character> inputbuffer, int textbuffer, int pointer,
            char zsciiChar);
}
