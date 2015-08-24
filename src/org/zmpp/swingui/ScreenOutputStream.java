/*
 * $Id: ScreenOutputStream.java 535 2008-02-19 06:02:50Z weiju $
 * 
 * Created on 2006/02/24
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
package org.zmpp.swingui;

import org.zmpp.encoding.ZsciiEncoding;
import org.zmpp.io.OutputStream;
import org.zmpp.vm.Machine;

/**
 * This class implements the screen output stream. It mainly acts as a
 * dispatcher to the current window.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class ScreenOutputStream implements OutputStream {

    private boolean isSelected;
    private Machine machine;
    private Viewport viewport;

    /**
     * Constructor.
     *
     * @param machine the machine object
     * @param viewport the viewport
     */
    public ScreenOutputStream(Machine machine, Viewport viewport) {
        this.machine = machine;
        this.viewport = viewport;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * {@inheritDoc}
     */
    public void select(boolean flag) {
        isSelected = flag;
    }

    /**
     * {@inheritDoc}
     */
    public void print(final char zsciiChar, boolean isInput) {
        //System.out.printf("@print %c (isInput: %b)\n", (char) zsciiChar, isInput);    
        if (zsciiChar == ZsciiEncoding.NEWLINE) {
            viewport.getCurrentWindow().printChar('\n', isInput);
        } else {
            viewport.getCurrentWindow().printChar(
                    machine.getGameData().getZsciiEncoding().getUnicodeChar(zsciiChar),
                    isInput);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void deletePrevious(char zchar) {
        char deleteChar
                = machine.getGameData().getZsciiEncoding().getUnicodeChar(zchar);
        viewport.getCurrentWindow().backspace(deleteChar);
    }

    /**
     * {@inheritDoc}
     */
    public void flush() {
        viewport.getCurrentWindow().flushBuffer();
    }

    /**
     * {@inheritDoc}
     */
    public void close() {
    }
}
