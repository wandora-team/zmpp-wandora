/*
 * $Id: RingBuffer.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/03/10
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
package org.zmpp.vmutil;

/**
 * This ring buffer implementation is an efficient representation for a dynamic
 * list structure that should have a limited number of entries and where the
 * oldest n entries can be discarded. This kind of container is particularly
 * useful for undo and history buffers.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class RingBuffer<T> {

    private T[] elements;
    private int bufferstart;
    private int bufferend;
    private int size;

    /**
     * Constructor.
     *
     * @param size the size of the buffer
     */
    @SuppressWarnings({"unchecked"})
    public RingBuffer(int size) {
        elements = (T[]) new Object[size];
    }

    /**
     * Adds an element to the buffer. If the capacity of the buffer is exceeded,
     * the oldest element is replaced.
     *
     * @param elem the element
     */
    public void add(final T elem) {
        bufferend = bufferend % elements.length;
        if (size == elements.length) {
            bufferstart = (bufferstart + 1) % elements.length;
        } else {
            size++;
        }
        elements[bufferend++] = elem;
    }

    /**
     * Replaces the element at the specified index with the specified element.
     *
     * @param index the replacement index
     * @param elem the replacement element
     */
    public void set(final int index, final T elem) {
        elements[mapIndex(index)] = elem;
    }

    /**
     * Returns the element at the specified index.
     *
     * @param index the index
     * @return the object
     */
    public T get(final int index) {
        return elements[mapIndex(index)];
    }

    /**
     * Returns the size of this ring buffer.
     *
     * @return the size
     */
    public int size() {
        return size;
    }

    /**
     * Removes the object at the specified index.
     *
     * @param index the index
     * @return the removed object
     */
    public T remove(final int index) {
        // remember the removed element
        final T elem = get(index);

        // move the following element by one to the front
        for (int i = index; i < (size - 1); i++) {
            final int idx1 = mapIndex(i);
            final int idx2 = mapIndex(i + 1);
            elements[idx1] = elements[idx2];
        }
        size--;
        bufferend = (bufferend - 1) % elements.length;
        return elem;
    }

    /**
     * Maps a container index to a ring buffer index.
     *
     * @param index the container index
     * @return the buffer index
     */
    private int mapIndex(final int index) {
        return (bufferstart + index) % elements.length;
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        final StringBuilder buffer = new StringBuilder("{ ");
        for (int i = 0; i < size(); i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            buffer.append(get(i));
        }
        buffer.append(" }");
        return buffer.toString();
    }
}
