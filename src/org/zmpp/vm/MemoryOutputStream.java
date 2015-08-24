/*
 * $Id: MemoryOutputStream.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 11/23/2005
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

import java.util.ArrayList;
import java.util.List;

import org.zmpp.base.Memory;
import org.zmpp.io.OutputStream;

/**
 * This class implements output stream 3. This stream writes to dynamic memory.
 * The stream contains a table address stack in order to support nested
 * selections.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class MemoryOutputStream implements OutputStream {

    /**
     * Maximum nesting depth for this stream.
     */
    private static final int MAX_NESTING_DEPTH = 16;

    static class TablePosition {

        int tableAddress;
        int bytesWritten;

        TablePosition(int tableAddress) {
            this.tableAddress = tableAddress;
        }
    }

    /**
     * The machine object.
     */
    private Machine machine;

    /**
     * Support nested selections.
     */
    private List<TablePosition> tableStack;

    /**
     * Constructor.
     *
     * @param machine the machine object
     */
    public MemoryOutputStream(Machine machine) {

        tableStack = new ArrayList<TablePosition>();
        this.machine = machine;
    }

    /**
     * {@inheritDoc}
     */
    public void print(final char zsciiChar, final boolean isInput) {

        //System.out.println("memory.print: " + ((char) zsciiChar));
        final TablePosition tablePos = tableStack.get(tableStack.size() - 1);
        final int position = tablePos.tableAddress + 2 + tablePos.bytesWritten;
        machine.getGameData().getMemory().writeUnsignedByte(
                position, (short) zsciiChar);
        tablePos.bytesWritten++;
    }

    /**
     * {@inheritDoc}
     */
    public void flush() {

        // intentionally left empty
    }

    /**
     * {@inheritDoc}
     */
    public void close() {

        // intentionally left empty
    }

    /**
     * {@inheritDoc}
     */
    public void deletePrevious(final char zchar) {
        // intentionally left empty
    }

    /**
     * {@inheritDoc}
     */
    public void select(final boolean flag) {

        if (!flag && tableStack.size() > 0) {

      // Write the total number of written bytes to the first word
            // of the table
            final TablePosition tablePos = tableStack.remove(tableStack.size() - 1);
      //System.out.println("deselect stream 3, popping off: "
            //                   + tablePos.tableAddress + " # bytes: "
            //                   + tablePos.bytesWritten);
            machine.getGameData().getMemory().writeUnsignedShort(
                    tablePos.tableAddress, tablePos.bytesWritten);

            if (machine.getGameData().getStoryFileHeader().getVersion() == 6) {

                writeTextWidthInUnits(tablePos);
            }
        }
    }

    private void writeTextWidthInUnits(TablePosition tablepos) {

        int numwords = tablepos.bytesWritten;
        char[] data = new char[numwords];
        Memory memory = machine.getGameData().getMemory();

        for (int i = 0; i < numwords; i++) {

            data[i] = (char) memory.readUnsignedByte(tablepos.tableAddress + i + 2);
        }
        machine.getScreen6().setTextWidthInUnits(data);
    }

    /**
     * Selects this memory stream.
     *
     * @param tableAddress the table address
     * @param tableWidth the table width
     */
    public void select(final int tableAddress, final int tableWidth) {

    //this.tableWidth = tableWidth;
        if (tableStack.size() < MAX_NESTING_DEPTH) {

            tableStack.add(new TablePosition(tableAddress));

        } else {

            machine.getCpu().halt("maximum nesting depth (16) for stream 3 exceeded");
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSelected() {

        return !tableStack.isEmpty();
    }
}
