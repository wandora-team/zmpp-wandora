/*
 * $Id: Instruction.java 536 2008-02-19 06:03:27Z weiju $
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

/**
 * This interface defines an instruction's public methods.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface Instruction {

    /**
     * Execute the instruction.
     */
    void execute();

    /**
     * Returns true if this instruction prints output.
     *
     * @return true if prints output, false otherwise
     */
    boolean isOutput();
}
