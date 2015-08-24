/*
 * $Id: ClassicObjectTree.java 536 2008-02-19 06:03:27Z weiju $
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
 * This class implements the object tree for story file version <= 3.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class ClassicObjectTree extends AbstractObjectTree {

    private static final int OFFSET_PARENT = 4;
    private static final int OFFSET_SIBLING = 5;
    private static final int OFFSET_CHILD = 6;
    private static final int OFFSET_PROPERTYTABLE = 7;

    /**
     * Object entries in version <= 3 have a size of 9 bytes.
     */
    private static final int OBJECTENTRY_SIZE = 9;

    /**
     * Property defaults entries in versions <= 3 have a size of 31 words.
     */
    private static final int PROPERTYDEFAULTS_SIZE = 31 * 2;

    /**
     * Constructor.
     *
     * @param memory the Memory object
     * @param address the address
     */
    public ClassicObjectTree(Memory memory, int address) {
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

    /**
     * {@inheritDoc}
     */
    public int getPropertyLength(final int propertyAddress) {
        return getPropertyLengthAtData(getMemory(), propertyAddress);
    }

    /**
     * {@inheritDoc}
     */
    public int getChild(final int objectNum) {
        return getMemory().readUnsignedByte(getObjectAddress(objectNum)
                + OFFSET_CHILD);
    }

    /**
     * {@inheritDoc}
     */
    public void setChild(final int objectNum, final int child) {
        getMemory().writeUnsignedByte(getObjectAddress(objectNum) + OFFSET_CHILD,
                (short) (child & 0xff));
    }

    /**
     * {@inheritDoc}
     */
    public int getParent(final int objectNum) {
        return getMemory().readUnsignedByte(getObjectAddress(objectNum)
                + OFFSET_PARENT);
    }

    /**
     * {@inheritDoc}
     */
    public void setParent(final int objectNum, final int parent) {
        getMemory().writeUnsignedByte(getObjectAddress(objectNum) + OFFSET_PARENT,
                (short) (parent & 0xff));
    }

    /**
     * {@inheritDoc}
     */
    public int getSibling(final int objectNum) {
        return getMemory().readUnsignedByte(getObjectAddress(objectNum)
                + OFFSET_SIBLING);
    }

    /**
     * {@inheritDoc}
     */
    public void setSibling(final int objectNum, final int sibling) {
        getMemory().writeUnsignedByte(getObjectAddress(objectNum) + OFFSET_SIBLING,
                (short) (sibling & 0xff));
    }

    /**
     * {@inheritDoc}
     */
    protected int getPropertyTableAddress(final int objectNum) {
        return getMemory().readUnsignedShort(getObjectAddress(objectNum)
                + OFFSET_PROPERTYTABLE);
    }

    /**
     * {@inheritDoc}
     */
    protected int getNumPropertySizeBytes(final int propertyDataAddress) {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    protected int getNumPropSizeBytesAtData(int propertyDataAddress) {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    protected int getPropertyNum(final int propertyAddress) {
        final int sizeByte = getMemory().readUnsignedByte(propertyAddress);
        return sizeByte - 32 * (getPropertyLength(propertyAddress + 1) - 1);
    }

    /**
     * This function represents the universal formula to calculate the length of
     * a property given the address of its data (as opposed to the address of
     * the property itself).
     *
     * @param memaccess the memory access object
     * @param addressOfPropertyData the address of the property data
     * @return the length of the property
     */
    private static int getPropertyLengthAtData(final Memory memaccess,
            final int addressOfPropertyData) {
        if (addressOfPropertyData == 0) {
            return 0; // see standard 1.1
        }

    // The size byte is always the byte before the property data in any
        // version, so this is consistent
        final short sizebyte
                = memaccess.readUnsignedByte(addressOfPropertyData - 1);

        return sizebyte / 32 + 1;
    }
}
