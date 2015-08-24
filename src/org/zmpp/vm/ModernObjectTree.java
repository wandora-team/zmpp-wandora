/*
 * $Id: ModernObjectTree.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/03/05
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

import org.zmpp.base.Memory;

/**
 * This class implements the object tree for story file version >= 4.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class ModernObjectTree extends AbstractObjectTree {

    private static final int OFFSET_PARENT = 6;
    private static final int OFFSET_SIBLING = 8;
    private static final int OFFSET_CHILD = 10;
    private static final int OFFSET_PROPERTYTABLE = 12;

    /**
     * Object entries in version >= 4 have a size of 14 bytes.
     */
    private static final int OBJECTENTRY_SIZE = 14;

    /**
     * Property defaults entries in versions >= 4 have a size of 63 words.
     */
    private static final int PROPERTYDEFAULTS_SIZE = 63 * 2;

    public ModernObjectTree(Memory memory, int address) {
        super(memory, address);
    }

    /**
     * {@inheritDoc}
     */
    protected int getObjectAddress(int objectNum) {
        return getObjectTreeStart() + (objectNum - 1) * getObjectEntrySize();
    }

    /**
     * {@inheritDoc}
     */
    protected int getPropertyDefaultsSize() {
        return PROPERTYDEFAULTS_SIZE;
    }

    /**
     * {@inheritDoc}
     */
    protected int getObjectEntrySize() {
        return OBJECTENTRY_SIZE;
    }

  // ************************************************************************
    // ****** Object methods
    // ****************************
    /**
     * {@inheritDoc}
     */
    public int getParent(final int objectNum) {
        return getMemory().readUnsignedShort(getObjectAddress(objectNum)
                + OFFSET_PARENT);
    }

    /**
     * {@inheritDoc}
     */
    public void setParent(final int objectNum, final int parent) {
        getMemory().writeUnsignedShort(getObjectAddress(objectNum) + OFFSET_PARENT,
                parent);
    }

    /**
     * {@inheritDoc}
     */
    public int getSibling(final int objectNum) {
        return getMemory().readUnsignedShort(getObjectAddress(objectNum)
                + OFFSET_SIBLING);
    }

    /**
     * {@inheritDoc}
     */
    public void setSibling(final int objectNum, final int sibling) {
        getMemory().writeUnsignedShort(getObjectAddress(objectNum) + OFFSET_SIBLING,
                sibling);
    }

    /**
     * {@inheritDoc}
     */
    public int getChild(final int objectNum) {
        return getMemory().readUnsignedShort(getObjectAddress(objectNum)
                + OFFSET_CHILD);
    }

    /**
     * {@inheritDoc}
     */
    public void setChild(final int objectNum, final int child) {
        getMemory().writeUnsignedShort(getObjectAddress(objectNum) + OFFSET_CHILD,
                child);
    }

  // ************************************************************************
    // ****** Property methods
    // ****************************
    /**
     * {@inheritDoc}
     */
    public int getPropertyLength(final int propertyAddress) {
        return getPropertyLengthAtData(getMemory(), propertyAddress);
    }

    /**
     * {@inheritDoc}
     */
    protected int getPropertyTableAddress(int objectNum) {
        return getMemory().readUnsignedShort(getObjectAddress(objectNum)
                + OFFSET_PROPERTYTABLE);
    }

    /**
     * {@inheritDoc}
     */
    protected int getNumPropertySizeBytes(final int propertyAddress) {
        // if bit 7 is set, there are two size bytes, one otherwise
        final short first = getMemory().readUnsignedByte(propertyAddress);
        return ((first & 0x80) > 0) ? 2 : 1;
    }

    /**
     * {@inheritDoc}
     */
    protected int getNumPropSizeBytesAtData(int propertyDataAddress) {
        return getNumPropertySizeBytes(propertyDataAddress - 1);
    }

    /**
     * {@inheritDoc}
     */
    protected int getPropertyNum(final int propertyAddress) {
        // Version >= 4 - take the lower 5 bit of the first size byte
        return getMemory().readUnsignedByte(propertyAddress) & 0x3f;
    }

    /**
     * This function represents the universal formula to calculate the length of
     * a property given the address of its data (as opposed to the address of
     * the property itself).
     *
     * @param memory the Memory object
     * @param addressOfPropertyData the address of the property data
     * @return the length of the property
     */
    private static int getPropertyLengthAtData(final Memory memory,
            final int addressOfPropertyData) {
        if (addressOfPropertyData == 0) {
            return 0; // see standard 1.1
        }
    // The size byte is always the byte before the property data in any
        // version, so this is consistent
        final short sizebyte
                = memory.readUnsignedByte(addressOfPropertyData - 1);

        // Bit 7 set => this is the second size byte
        if ((sizebyte & 0x80) > 0) {
            int proplen = sizebyte & 0x3f;
            if (proplen == 0) {
                proplen = 64; // Std. doc. 1.0, S 12.4.2.1.1
            }
            return proplen;
        } else {
      // Bit 7 clear => there is only one size byte, so if bit 6 is set,
            // the size is 2, else it is 1
            return (sizebyte & 0x40) > 0 ? 2 : 1;
        }
    }
}
