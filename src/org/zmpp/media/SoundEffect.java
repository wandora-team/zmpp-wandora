/*
 * $Id: SoundEffect.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/02/06
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
 * This class defines the interface to play a Blorb sound.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface SoundEffect {

    /**
     * Plays a sound the specified number of times.
     *
     * @param number the number of times to play
     * @param volume the volume at which the sound should be played
     */
    void play(int number, int volume);

    /**
     * Stops a sound.
     */
    void stop();

    /**
     * Adds a listener to listen for the sound stop event.
     *
     * @param l the listener
     */
    void addSoundStopListener(SoundStopListener l);

    /**
     * Removes a SoundStopListener.
     *
     * @param l the listener
     */
    void removeSoundStopListener(SoundStopListener l);
}
