/*
 * $Id: ObjectTree.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 10/14/2005
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
 * This is the interface definition of the object tree.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface ObjectTree {

    /**
     * Removes an object from its parent.
     *
     * @param objectNum the object number
     */
    void removeObject(int objectNum);

    /**
     * Inserts an object to a new parent.
     *
     * @param parentNum the parent number
     * @param objectNum the object number
     */
    void insertObject(int parentNum, int objectNum);

    /**
     * Determines the length of the property at the specified address. The
     * address is an address returned by ZObject.getPropertyAddress, i.e. it is
     * starting after the length byte.
     *
     * @param propertyAddress the property address
     * @return the length
     */
    int getPropertyLength(int propertyAddress);

  // ********************************************************************
    // ***** Methods on objects
    // ***********************************  
    /**
     * Tests if the specified attribute is set.
     *
     * @param objectNum the object number
     * @param attributeNum the attribute number, starting with 0
     * @return true if the attribute is set
     */
    boolean isAttributeSet(int objectNum, int attributeNum);

    /**
     * Sets the specified attribute.
     *
     * @param objectNum the object number
     * @param attributeNum the attribute number, starting with 0
     */
    void setAttribute(int objectNum, int attributeNum);

    /**
     * Clears the specified attribute.
     *
     * @param objectNum the object number
     * @param attributeNum the attribute number, starting with 0
     */
    void clearAttribute(int objectNum, int attributeNum);

    /**
     * Returns the number of this object's parent object.
     *
     * @return the parent object's number
     */
    int getParent(int objectNum);

    /**
     * Assigns a new parent object.
     *
     * @param objectNum the object number
     * @param parent the new parent object
     */
    void setParent(int objectNum, int parent);

    /**
     * Returns the object number of this object's sibling object.
     *
     * @param objectNum the object number
     * @return the sibling object's object number
     */
    int getSibling(int objectNum);

    /**
     * Assigns a new sibling to this object.
     *
     * @param objectNum the object number
     * @param sibling the new sibling's object number
     */
    void setSibling(int objectNum, int sibling);

    /**
     * Returns the object number of this object's child object.
     *
     * @param objectNum the object number
     * @return the child object's object number
     */
    int getChild(int objectNum);

    /**
     * Assigns a new child to this object.
     *
     * @param objectNum the object number
     * @param child the new child
     */
    void setChild(int objectNum, int child);

    /**
     * Returns the properties description address.
     *
     * @param objectNum the object number
     * @return the description address
     */
    int getPropertiesDescriptionAddress(int objectNum);

    /**
     * Returns the address of the specified property. Note that this will not
     * include the length byte.
     *
     * @param objectNum the object number
     * @param property the property
     * @return the specified property's address
     */
    int getPropertyAddress(int objectNum, int property);

    /**
     * Returns the next property in the list. If property is 0, this will return
     * the first property number, if property is the last element in the list,
     * it will return 0.
     *
     * @param objectNum the object number
     * @param property the property number
     * @return the next property in the list or 0
     */
    int getNextProperty(int objectNum, int property);

    /**
     * Returns the the specified property.
     *
     * @param objectNum the object number
     * @param property the property number
     * @return the value of the specified property
     */
    int getProperty(int objectNum, int property);

    /**
     * Sets the specified property byte to the given value.
     *
     * @param objectNum the object number
     * @param property the property
     * @param value the value
     */
    void setProperty(int objectNum, int property, int value);
}
