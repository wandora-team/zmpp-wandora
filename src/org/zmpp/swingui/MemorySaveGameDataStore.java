/*
 * $Id: MemorySaveGameDataStore.java 535 2008-02-19 06:02:50Z weiju $
 * 
 * Created on 2006/03/22
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

import org.zmpp.iff.FormChunk;
import org.zmpp.iff.WritableFormChunk;
import org.zmpp.vm.SaveGameDataStore;

/**
 * This class saves save games as a memory object.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class MemorySaveGameDataStore implements SaveGameDataStore {

    private WritableFormChunk savegame;

    /**
     * {@inheritDoc}
     */
    public boolean saveFormChunk(WritableFormChunk formchunk) {

        savegame = formchunk;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public FormChunk retrieveFormChunk() {

        return savegame;
    }
}
