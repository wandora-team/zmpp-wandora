/*
 * $Id: Interruptable.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/02/10
 * Copyright 2005-2008 by Wei-ju Wu
 *
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
package org.zmpp.base;

/**
 * This interface indicates that objects implementing it can interrupt their
 * current execution temporarily, executing the given routine and returning to
 * the former execution after finishing with that routine.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface Interruptable {

    /**
     * Indicates to the receiver that a interrupt should be started.
     *
     * @param routineAddress the routine address
     */
    void setInterruptRoutine(int routineAddress);
}
