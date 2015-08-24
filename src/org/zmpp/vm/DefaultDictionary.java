/*
 * $Id: DefaultDictionary.java 536 2008-02-19 06:03:27Z weiju $
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

import java.util.HashMap;
import java.util.Map;

import org.zmpp.base.Memory;
import org.zmpp.encoding.ZCharDecoder;
import org.zmpp.encoding.ZsciiString;

/**
 * This class implements a view on the dictionary within a memory map. Since it
 * takes the implementations of getN
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class DefaultDictionary extends AbstractDictionary {

    /**
     * The lookup map.
     */
    private Map<ZsciiString, Integer> lookupMap;

    /**
     * The maximum entry size.
     */
    private int maxEntrySize;

    /**
     * Constructor.
     *
     * @param memory the memory object
     * @param address the start address of the dictionary
     * @param converter a Z char decoder object
     * @param sizes a sizes object
     */
    public DefaultDictionary(Memory memory, int address,
            ZCharDecoder decoder, DictionarySizes sizes) {
        super(memory, address, decoder, sizes);
        createLookupMap();
    }

    /**
     * {@inheritDoc}
     */
    public int lookup(final ZsciiString token) {
        final ZsciiString lookupToken = truncateToken(token);

        if (lookupMap.containsKey(lookupToken)) {

            //System.out.println("Found, entry: " + lookupMap.get(entry));
            return lookupMap.get(lookupToken);
        }
        //System.out.println("Not found, token: '" + token + "'");
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    protected int getMaxEntrySize() {
        return maxEntrySize;
    }

    /**
     * Create the dictionary lookup map. The standards document suggests to
     * convert the tokens into ZSCII strings and look them up in the dictionary
     * by a binary search algorithm, which results in a O(log n) search
     * algorithm, instead I convert the dictionary strings into Java strings and
     * put them into a (entry - address) map, which is easier to handle and is
     * O(1). Generating it once at initialization is safe because the dictionary
     * is in static memory and does not change at runtime.
     */
    private void createLookupMap() {
        lookupMap = new HashMap<ZsciiString, Integer>();
        int entryAddress;

        for (int i = 0, n = getNumberOfEntries(); i < n; i++) {
            entryAddress = getEntryAddress(i);
            final ZsciiString str = getDecoder().decode2Zscii(getMemory(),
                    entryAddress, getSizes().getNumEntryBytes());
            maxEntrySize = Math.max(str.length(), maxEntrySize);
            lookupMap.put(str, entryAddress);
        }
    }
}
