/*
 * $Id: MediaCollection.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/02/12
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
package org.zmpp.media;

/**
 * This interfaces defines the common functions of a media resource collection.
 * A MediaCollection manages one specific type of media, e.g. sound effects or
 * pictures. Resources might be loaded lazily and cached in an internal cache.
 *
 * @author Wei-ju Wu
 * @version 1.0
 *
 * @param <T> the media type this collection manages
 */
public interface MediaCollection<T> {

    void clear();

    /**
     * Accesses the resource.
     *
     * @param number the number of the resource
     * @return the resource
     */
    T getResource(int number);

    /**
     * Loads a resource into the internal cache if this collection supports
     * caching.
     *
     * @param number the number of the resource
     */
    void loadResource(int number);

    /**
     * Throws the resource out of the internal cache if this collection supports
     * caching.
     *
     * @param number the number of the resource
     */
    void unloadResource(int number);

    /**
     * Returns the number of resources.
     *
     * @return the number of resources
     */
    int getNumResources();
}
