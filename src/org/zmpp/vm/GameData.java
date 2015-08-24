/*
 * $Id: GameData.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 10/10/2005
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
import org.zmpp.encoding.AlphabetTable;
import org.zmpp.encoding.ZCharDecoder;
import org.zmpp.encoding.ZCharEncoder;
import org.zmpp.encoding.ZsciiEncoding;
import org.zmpp.media.Resources;

/**
 * Interface to access the game specific objects.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface GameData {

    /**
     * Resets the data.
     */
    void reset();

    /**
     * Returns the file data as a Memory object.
     *
     * @return the file data
     */
    Memory getMemory();

    /**
     * Returns the story file header.
     *
     * @return the story file header
     */
    StoryFileHeader getStoryFileHeader();

    /**
     * Returns the dictionary for this game.
     *
     * @return the dictionary
     */
    Dictionary getDictionary();

    /**
     * Returns the object tree for this game.
     *
     * @return the object tree
     */
    ObjectTree getObjectTree();

    /**
     * Returns the Z char decoder for this game.
     *
     * @return the z char decoder
     */
    ZCharDecoder getZCharDecoder();

    /**
     * Returns the Z char encoder for this game.
     *
     * @return the z char encoder
     */
    ZCharEncoder getZCharEncoder();

    /**
     * Returns the ZSCII encoding object.
     *
     * @return the encoding object
     */
    ZsciiEncoding getZsciiEncoding();

    /**
     * Returns this game's alphabet table.
     *
     * @return the alphabet table
     */
    AlphabetTable getAlphabetTable();

    /**
     * Returns the multimedia resources.
     *
     * @return the multimedia resources
     */
    Resources getResources();

    /**
     * Returns the calculated check sum.
     *
     * @return the calculated check sum
     */
    int getCalculatedChecksum();

    /**
     * Returns true, if the checksum validation was successful.
     *
     * @return true if checksum is valid
     */
    boolean hasValidChecksum();
}
