/*
 * $Id: AbstractObjectTree.java 536 2008-02-19 06:03:27Z weiju $
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
 * This class is the abstract super class of object trees.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public abstract class AbstractObjectTree implements ObjectTree {

    private Memory memory;
    private int address;

    /**
     * Constructor.
     *
     * @param memory the memory access object
     * @param address the object table's start address
     */
    public AbstractObjectTree(final Memory memory, final int address) {
        this.memory = memory;
        this.address = address;
    }

    /**
     * Returns the memory object.
     *
     * @return the memory object
     */
    protected Memory getMemory() {
        return memory;
    }

    /**
     * Returns this tree's start address.
     *
     * @return the address
     */
    protected int getAddress() {
        return address;
    }

    /**
     * Returns the address of the specified object.
     *
     * @param objectNum the object number
     * @return the object address
     */
    protected abstract int getObjectAddress(int objectNum);

    /**
     * {@inheritDoc}
     */
    public void removeObject(final int objectNum) {
        int oldParent = getParent(objectNum);
        setParent(objectNum, 0);

        if (oldParent != 0) {
            if (getChild(oldParent) == objectNum) {
                setChild(oldParent, getSibling(objectNum));
            } else {
        // Find the child that comes directly before the removed
                // node and set the direct sibling of the removed node as
                // its new sibling
                int currentChild = getChild(oldParent);
                int sibling = getSibling(currentChild);

        // We have to handle the case that in fact that object is a child
                // of its parent, but not directly (happens for some reasons).
                // We stop in this case and simply remove the object from its
                // parent, probably the object tree modification routines should
                // be reverified
                while (sibling != 0 && sibling != objectNum) {
                    currentChild = sibling;
                    sibling = getSibling(currentChild);
                }
        // sibling might be 0, in that case, the object is not
                // in the hierarchy
                if (sibling == objectNum) {
                    setSibling(currentChild, getSibling(objectNum));
                }
            }
        }
        setSibling(objectNum, 0);
    }

    /**
     * {@inheritDoc}
     */
    public void insertObject(final int parentNum, final int objectNum) {
        // we want to ensure, the child has no old parent relationships
        if (getParent(objectNum) > 0) {
            removeObject(objectNum);
        }
        final int oldChild = getChild(parentNum);
        setParent(objectNum, parentNum);
        setChild(parentNum, objectNum);
        setSibling(objectNum, oldChild);
    }

    /**
     * The size of the property defaults section.
     *
     * @return the property defaults section
     */
    protected abstract int getPropertyDefaultsSize();

    /**
     * Returns the start address of the object tree section.
     *
     * @return the object tree's start address
     */
    protected int getObjectTreeStart() {
        return getAddress() + getPropertyDefaultsSize();
    }

    /**
     * Returns the story file version specific object entry size.
     *
     * @return the size of an object entry
     */
    protected abstract int getObjectEntrySize();

  // ******************************************************************
    // ****** Object methods
    // ****************************
    /**
     * {@inheritDoc}
     */
    public boolean isAttributeSet(int objectNum, int attributeNum) {
        final short value = memory.readUnsignedByte(
                getAttributeByteAddress(objectNum, attributeNum));
        return (value & (0x80 >> (attributeNum & 7))) > 0;
    }

    /**
     * {@inheritDoc}
     */
    public void setAttribute(int objectNum, int attributeNum) {
        final int attributeByteAddress = getAttributeByteAddress(objectNum,
                attributeNum);
        short value = memory.readUnsignedByte(attributeByteAddress);
        value |= (0x80 >> (attributeNum & 7));
        memory.writeUnsignedByte(attributeByteAddress, value);
    }

    /**
     * {@inheritDoc}
     */
    public void clearAttribute(int objectNum, int attributeNum) {
        final int attributeByteAddress = getAttributeByteAddress(objectNum,
                attributeNum);
        short value = memory.readUnsignedByte(attributeByteAddress);
        value &= (~(0x80 >> (attributeNum & 7)));
        memory.writeUnsignedByte(attributeByteAddress, value);
    }

    /**
     * Returns the address of the byte specified object attribute lies in.
     *
     * @param objectNum the object number
     * @param attributeNum the attribute number
     * @return the address of the attribute byte
     */
    private int getAttributeByteAddress(int objectNum, int attributeNum) {
        return getObjectAddress(objectNum) + attributeNum / 8;
    }

  // ******************************************************************
    // ****** Property methods
    // ****************************
    /**
     * {@inheritDoc}
     */
    public int getPropertiesDescriptionAddress(final int objectNum) {
        return getPropertyTableAddress(objectNum) + 1;
    }

    /**
     * {@inheritDoc}
     */
    public int getPropertyAddress(final int objectNum, final int property) {
        int propAddr = getPropertyEntriesStart(objectNum);
        while (true) {
            int propnum = getPropertyNum(propAddr);
            if (propnum == 0) {
                return 0; // not found
            }
            if (propnum == property) {
                return propAddr + getNumPropertySizeBytes(propAddr);
            }
            int numPropBytes = getNumPropertySizeBytes(propAddr);
            propAddr += numPropBytes + getPropertyLength(propAddr + numPropBytes);
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getNextProperty(final int objectNum, final int property) {
        if (property == 0) {
            final int addr = getPropertyEntriesStart(objectNum);
            return getPropertyNum(addr);
        }
        int propDataAddr = getPropertyAddress(objectNum, property);
        if (propDataAddr == 0) {
            reportPropertyNotAvailable(objectNum, property);
            return 0;
        } else {
            return getPropertyNum(propDataAddr + getPropertyLength(propDataAddr));
        }
    }

    private void reportPropertyNotAvailable(int objectNum, int property) {
        throw new IllegalArgumentException("Property " + property
                + " of object " + objectNum + " is not available.");
    }

    /**
     * {@inheritDoc}
     */
    public int getProperty(int objectNum, int property) {
        int propertyDataAddress = getPropertyAddress(objectNum, property);
        if (propertyDataAddress == 0) {
            return getPropertyDefault(property);
        }
        final int numBytes = getPropertyLength(propertyDataAddress);
        int value;
        if (numBytes == 1) {
            value = memory.readUnsignedByte(propertyDataAddress) & 0xff;
        } else {
            final int byte1 = memory.readUnsignedByte(propertyDataAddress);
            final int byte2 = memory.readUnsignedByte(propertyDataAddress + 1);
            value = (byte1 << 8 | (byte2 & 0xff));
        }
        return value & 0xffff;
    }

    /**
     * {@inheritDoc}
     */
    public void setProperty(int objectNum, int property, int value) {

        int propertyDataAddress = getPropertyAddress(objectNum, property);
        if (propertyDataAddress == 0) {
            reportPropertyNotAvailable(objectNum, property);
        } else {
            int propsize = getPropertyLength(propertyDataAddress);
            if (propsize == 1) {
                memory.writeUnsignedByte(propertyDataAddress, (short) (value & 0xff));
            } else {
                memory.writeUnsignedShort(propertyDataAddress, value & 0xffff);
            }
        }
    }

    /**
     * Returns the property number at the specified table index.
     *
     * @param index the property address
     * @return the property number
     */
    protected abstract int getPropertyNum(int propertyAddress);

    /**
     * Returns the address of an object's property table.
     *
     * @param objectNum the object number
     * @return the table address
     */
    protected abstract int getPropertyTableAddress(int objectNum);

    /**
     * Returns the number of property size bytes at the specified address.
     *
     * @param propertyAddress the address of the property entry
     * @return the number of size bytes
     */
    protected abstract int getNumPropertySizeBytes(int propertyAddress);

    /**
     * Returns the number of property size bytes at the specified property data
     * address.
     *
     * @param propertyDataAddress the address of the property entry data
     * @return the number of size bytes
     */
    protected abstract int getNumPropSizeBytesAtData(int propertyDataAddress);

    /**
     * Returns the start address of the actual property entries.
     *
     * @param objectNum the object number
     * @return the property entries' start address
     */
    private int getPropertyEntriesStart(int objectNum) {
        return getPropertyTableAddress(objectNum)
                + getDescriptionHeaderSize(objectNum);
    }

    /**
     * Returns the size of the description header in bytes that is, the size
     * byte plus the description string size. This stays the same for all story
     * file versions.
     *
     * @param objectNum the object number
     * @return the size of the description header
     */
    private int getDescriptionHeaderSize(int objectNum) {
        final int startAddr = getPropertyTableAddress(objectNum);
        return memory.readUnsignedByte(startAddr) * 2 + 1;
    }

    /**
     * Returns the property default value at the specified position in the
     * property defaults table.
     *
     * @param propertyNum the default entry's property number
     * @return the property default value
     */
    private short getPropertyDefault(final int propertyNum) {
        final int index = propertyNum - 1;
        return memory.readShort(address + index * 2);
    }
}
