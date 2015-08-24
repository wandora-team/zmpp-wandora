/*
 * $Id: PrintLiteralStaticInfo.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2005/12/19
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
package org.zmpp.instructions;

/**
 * The static aspects of a PrintLiteralInstruction are stored here.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class PrintLiteralStaticInfo implements InstructionStaticInfo {

    /**
     * The valid versions.
     */
    private static final int[][] VALID_VERSIONS = {
        {}, // 0x00
        {}, // 0x01
        {1, 2, 3, 4, 5, 6, 7, 8}, // PRINT
        {1, 2, 3, 4, 5, 6, 7, 8} // PRINT_RET
    };

    /**
     * Opcodes.
     */
    public static final int OP_PRINT = 0x02;
    public static final int OP_PRINT_RET = 0x03;

    /**
     * Singleton instance.
     */
    private static final PrintLiteralStaticInfo instance
            = new PrintLiteralStaticInfo();

    /**
     * Returns an instance of PrintLiteralStaticInfo.
     *
     * @return a PrintLiteralStaticInfo object
     */
    public static PrintLiteralStaticInfo getInstance() {

        return instance;
    }

    /**
     * {@inheritDoc}
     */
    public int[] getValidVersions(final int opcode) {

        return (opcode < VALID_VERSIONS.length) ? VALID_VERSIONS[opcode]
                : new int[0];
    }

    /**
     * {@inheritDoc}
     */
    public boolean storesResult(final int opcode, final int version) {

        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isBranch(final int opcode, final int version) {

        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isOutput(final int opcode, final int version) {

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public String getOpName(final int opcode, final int version) {

        switch (opcode) {

            case OP_PRINT_RET:
                return "PRINT_RET";
            case OP_PRINT:
                return "PRINT";
            default:
                return "unknown";
        }
    }
}
