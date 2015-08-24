/*
 * $Id: FastShortStack.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/05/10
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
package org.zmpp.vmutil;

/**
 * This class implements a faster version of the Z-machin main stack. This
 * combines abstract access with the bypassing of unnecessary object creation.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public final class FastShortStack {

    private short[] values;
    private int stackpointer;

    /**
     * Constructor.
     *
     * @param size the stack size
     */
    public FastShortStack(final int size) {
        values = new short[size];
        stackpointer = 0;
    }

    /**
     * Returns the current stack pointer.
     *
     * @return the stack pointer
     */
    public int getStackPointer() {
        return stackpointer;
    }

    /**
     * Pushes a value on the stack and increases the stack pointer.
     *
     * @param value the value
     */
    public void push(final short value) {
        values[stackpointer++] = value;
    }

    /**
     * Returns the top value of the stack without modifying the stack pointer.
     *
     * @return the top value
     */
    public short top() {
        return values[stackpointer - 1];
    }

    /**
     * Replaces the top element with the specified value.
     *
     * @param value the value to replace
     */
    public void replaceTopElement(final short value) {
        values[stackpointer - 1] = value;
    }

    /**
     * Returns the size of the stack. Is equal to stack pointer, but has a
     * different semantic meaning.
     *
     * @return the size of the stack
     */
    public int size() {
        return stackpointer;
    }

    /**
     * Returns the top value of the stack and decreases the stack pointer.
     *
     * @return the top value
     */
    public short pop() {
        return values[--stackpointer];
    }

    /**
     * Returns the value at index of the stack, here stack is treated as an
     * array.
     *
     * @param index the index
     * @return the value at the index
     */
    public short getValueAt(int index) {
        return values[index];
    }
}
