/*
 * $Id: AlphabetTableV2.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/01/18
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
package org.zmpp.encoding;

/**
 * An alphabet table in a V2 story file behaves "almost like" the default
 * alphabet table, in that they have the same characters in the alphabets. There
 * are however two differences: It only supports one abbreviation code and it
 * supports shift-lock.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class AlphabetTableV2 extends DefaultAlphabetTable {

    /**
     * {@inheritDoc}
     */
    public boolean isAbbreviation(final char zchar) {
        return zchar == 1;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isShift1(final char zchar) {
        return zchar == SHIFT_2 || zchar == SHIFT_4;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isShift2(final char zchar) {
        return zchar == SHIFT_3 || zchar == SHIFT_5;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isShiftLock(final char zchar) {
        return zchar == SHIFT_4 || zchar == SHIFT_5;
    }
}
