/*
 * $Id: Machine.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 10/03/2005
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

import org.zmpp.media.PictureManager;
import org.zmpp.media.SoundSystem;

/**
 * This interface acts as a central access point to the Z-Machine's components.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public interface Machine extends ObjectTree {

    /**
     * Initialization function.
     *
     * @param machineConfig a configuration object
     * @param decoder the instruction decoder
     */
    void initialize(GameData machineConfig, InstructionDecoder decoder);

    /**
     * Returns the story file version.
     *
     * @return the story file version
     */
    int getVersion();

  // **********************************************************************
    // **** Main machine objects
    // *******************************
    /**
     * Returns the GameData object.
     *
     * @return the GameData object
     */
    GameData getGameData();

    /**
     * Returns the Cpu object.
     *
     * @return the Cpu object
     */
    Cpu getCpu();

    /**
     * Returns the Output object.
     *
     * @return the Output object
     */
    Output getOutput();

    /**
     * Returns the Input object.
     *
     * @return the Input object
     */
    Input getInput();

  // ************************************************************************
    // ****** Control functions
    // ************************************************
    /**
     * Restarts the virtual machine.
     */
    void restart();

    /**
     * Starts the virtual machine.
     */
    void start();

    /**
     * Exists the virtual machine.
     */
    void quit();

    /**
     * Outputs a warning message.
     *
     * @param msg
     */
    void warn(String msg);

  // **********************************************************************
    // **** Services
    // *******************************
    /**
     * Tokenizes the text in the text buffer using the specified parse buffer.
     *
     * @param textbuffer the text buffer
     * @param parsebuffer the parse buffer
     * @param dictionaryAddress the dictionary address or 0 for the default
     * dictionary
     * @param flag if set, unrecognized words are not written into the parse
     * buffer and their slots are left unchanged
     */
    void tokenize(int textbuffer, int parsebuffer, int dictionaryAddress,
            boolean flag);

    /**
     * Reads a string from the selected input stream.
     *
     * @param textbuffer the text buffer address in memory
     * @param time the time interval to call routine
     * @param routineAddress the packed routine address
     * @return the terminating character
     */
    char readLine(int textbuffer, int time, int routineAddress);

    /**
     * Reads a ZSCII char from the selected input stream.
     *
     * @param time the time interval to call routine (timed input)
     * @param routineAddress the packed routine address to call (timed input)
     * @return the selected ZSCII char
     */
    char readChar(int time, int routineAddress);

    /**
     * Returns the sound system.
     *
     * @return the sound system
     */
    SoundSystem getSoundSystem();

    /**
     * Returns the picture manager.
     *
     * @return the picture manager
     */
    PictureManager getPictureManager();

    /**
     * Generates a number in the range between 1 and <i>range</i>. If range is
     * negative, the random generator will be seeded to abs(range), if range is
     * 0, the random generator will be initialized to a new random seed. In both
     * latter cases, the result will be 0.
     *
     * @param range the range
     * @return a random number
     */
    short random(short range);

    /**
     * Updates the status line.
     */
    void updateStatusLine();

    /**
     * Sets the Z-machine's status line.
     *
     * @param statusline the status line
     */
    void setStatusLine(StatusLine statusline);

    /**
     * Sets the game screen.
     *
     * @param screen the screen model
     */
    void setScreen(ScreenModel screen);

    /**
     * Gets the game screen.
     *
     * @return the game screen
     */
    ScreenModel getScreen();

    /**
     * Returns screen model 6.
     *
     * @return screen model 6
     */
    ScreenModel6 getScreen6();

    /**
     * Sets the save game data store.
     *
     * @param datastore the data store
     */
    void setSaveGameDataStore(SaveGameDataStore datastore);

    /**
     * Saves the current state.
     *
     * @param savepc the save pc
     * @return true on success, false otherwise
     */
    boolean save(int savepc);

    /**
     * Saves the current state in memory.
     *
     * @param savepc the save pc
     * @return true on success, false otherwise
     */
    boolean save_undo(int savepc);

    /**
     * Restores a previously saved state.
     *
     * @return the portable game state
     */
    PortableGameState restore();

    /**
     * Restores a previously saved state from memory.
     *
     * @return the portable game state
     */
    PortableGameState restore_undo();
}
