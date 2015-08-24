/*
 * $Id: MachineFactory.java 563 2008-04-18 17:38:16Z weiju $
 * 
 * Created on 2006/02/15
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

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.zmpp.base.DefaultMemory;
import org.zmpp.blorb.BlorbResources;
import org.zmpp.blorb.BlorbStory;
import org.zmpp.iff.DefaultFormChunk;
import org.zmpp.iff.FormChunk;
import org.zmpp.instructions.DefaultInstructionDecoder;
import org.zmpp.io.FileInputStream;
import org.zmpp.io.IOSystem;
import org.zmpp.io.InputStream;
import org.zmpp.io.TranscriptOutputStream;
import org.zmpp.media.Resources;
import org.zmpp.vmutil.FileUtils;

/**
 * Constructing a Machine object is a very complex task, the building process
 * deals with creating the game objects, the UI and the I/O system. This factory
 * class offers a template for the building and leaves the concrete
 * implementation which are dependent on specific input sources and UI
 * technologies to sub classes.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public abstract class MachineFactory<T> {

    private byte[] storydata, blorbdata;
    private File storyfile, blorbfile;
    private URL storyurl, blorburl;
    private FormChunk blorbchunk;

    public MachineFactory(File storyfile, File blorbfile) {
        this.storyfile = storyfile;
        this.blorbfile = blorbfile;
    }

    public MachineFactory(File blorbfile) {
        this(null, blorbfile);
    }

    public MachineFactory(URL storyurl, URL blorburl) {
        this.storyurl = storyurl;
        this.blorburl = blorburl;
    }

    public MachineFactory(byte[] storydata, byte[] blorbdata) {
        this.storydata = storydata;
        this.blorbdata = blorbdata;
    }

    public MachineFactory(byte[] blorbdata) {
        this.blorbdata = blorbdata;
    }

    /**
     * This is the main creation function.
     *
     * @return the machine
     */
    public Machine buildMachine() throws IOException {

        final GameData gamedata = new GameDataImpl(readStoryData(), readResources());

        if (isInvalidStory(gamedata.getStoryFileHeader().getVersion())) {
            reportInvalidStory();
        }
        final Machine machine = new MachineImpl();
        final InstructionDecoder decoder = new DefaultInstructionDecoder();
        machine.initialize(gamedata, decoder);
        initUI(machine);
        initIOSystem(machine);
        return machine;
    }

    // ***********************************************************************
    // ****** Protected methods to be overridden
    // ***************************************************
    /**
     * Initializes the user interface objects.
     *
     * @param machine the machine object
     * @return the resulting top level user interface object
     */
    abstract protected T initUI(Machine machine);

    /**
     * Returns the top level user interface object.
     *
     * @return the top level user interface object
     */
    abstract public T getUI();

    /**
     * Reads the story data.
     *
     * @return the story data
     * @throws IOException if reading story file revealed an error
     */
    protected byte[] readStoryData() throws IOException {
        if (storydata != null) {
            return storydata;
        }
        if (blorbdata != null) {
            return new BlorbStory(new DefaultFormChunk(new DefaultMemory(blorbdata))).getStoryData();
        }
        if (storyfile != null || blorbfile != null) {
            return readStoryDataFromFile();
        }
        if (storyurl != null || blorburl != null) {
            return readStoryDataFromUrl();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    private byte[] readStoryDataFromUrl() throws IOException {
        java.io.InputStream storyis = null, blorbis = null;
        try {
            if (storyurl != null) {
                storyis = storyurl.openStream();
            }
            if (blorburl != null) {
                blorbis = blorburl.openStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (storyis != null) {
            return FileUtils.readFileBytes(storyis);
        } else {
            return new BlorbStory(readBlorb(blorbis)).getStoryData();
        }
    }

    /**
     * {@inheritDoc}
     */
    private byte[] readStoryDataFromFile() throws IOException {
        if (storyfile != null) {
            return FileUtils.readFileBytes(storyfile);
        } else {
            // Read from Z BLORB
            FormChunk formchunk = readBlorbFromFile();
            return formchunk != null ? new BlorbStory(formchunk).getStoryData() : null;
        }
    }

    /**
     * Reads the resource data.
     *
     * @return the resource data
     * @throws IOException if reading resources revealed an error
     */
    protected Resources readResources() throws IOException {
        if (blorbfile != null) {
            return readResourcesFromFile();
        }
        if (blorburl != null) {
            return readResourcesFromUrl();
        }
        return null;
    }

    private FormChunk readBlorbFromFile() throws IOException {
        if (blorbchunk == null) {
            byte[] data = FileUtils.readFileBytes(blorbfile);
            if (data != null) {
                blorbchunk = new DefaultFormChunk(new DefaultMemory(data));
                if (!"IFRS".equals(new String(blorbchunk.getSubId()))) {
                    throw new IOException("not a valid Blorb file");
                }
            }
        }
        return blorbchunk;
    }

    private Resources readResourcesFromFile() throws IOException {
        FormChunk formchunk = readBlorbFromFile();
        return (formchunk != null) ? new BlorbResources(formchunk) : null;
    }

    private FormChunk readBlorb(java.io.InputStream blorbis) throws IOException {
        if (blorbchunk == null) {
            byte[] data = FileUtils.readFileBytes(blorbis);
            if (data != null) {
                blorbchunk = new DefaultFormChunk(new DefaultMemory(data));
            }
        }
        return blorbchunk;
    }

    private Resources readResourcesFromUrl() throws IOException {
        FormChunk formchunk = readBlorb(blorburl.openStream());
        return (formchunk != null) ? new BlorbResources(formchunk) : null;
    }

    /**
     * This function is called to report an invalid story file.
     */
    abstract protected void reportInvalidStory();

    /**
     * The IOSystem object.
     *
     * @return the IOSystem object
     */
    abstract protected IOSystem getIOSystem();

    /**
     * The keyboard input stream object.
     *
     * @return the keyboard input stream
     */
    abstract protected InputStream getKeyboardInputStream();

    /**
     * Returns the status line object.
     *
     * @return the status line object
     */
    abstract protected StatusLine getStatusLine();

    /**
     * Returns the screen model object.
     *
     * @return the screen model
     */
    abstract protected ScreenModel getScreenModel();

    /**
     * Returns the save game data store object.
     *
     * @return the save game data store
     */
    abstract protected SaveGameDataStore getSaveGameDataStore();

    // ************************************************************************
    // ****** Private methods
    // ********************************
    /**
     * Checks the story file version.
     *
     * @param version the story file version
     * @return true if not supported
     */
    private boolean isInvalidStory(final int version) {
        return version < 1 || version > 8;
    }

    /**
     * Initializes the I/O system.
     *
     * @param machine the machine object
     */
    private void initIOSystem(final Machine machine) {
        initInputStreams(machine);
        initOutputStreams(machine);
        machine.setStatusLine(getStatusLine());
        machine.setScreen(getScreenModel());
        machine.setSaveGameDataStore(getSaveGameDataStore());
    }

    /**
     * Initializes the input streams.
     *
     * @param machine the machine object
     */
    private void initInputStreams(final Machine machine) {
        machine.getInput().setInputStream(0, getKeyboardInputStream());
        machine.getInput().setInputStream(1, new FileInputStream(getIOSystem(),
                machine.getGameData().getZsciiEncoding()));
    }

    /**
     * Initializes the output streams.
     *
     * @param machine the machine object
     */
    private void initOutputStreams(final Machine machine) {
        final Output output = machine.getOutput();
        output.setOutputStream(1, getScreenModel().getOutputStream());
        output.selectOutputStream(1, true);
        output.setOutputStream(2, new TranscriptOutputStream(
                getIOSystem(), machine.getGameData().getZsciiEncoding()));
        output.selectOutputStream(2, false);
        output.setOutputStream(3, new MemoryOutputStream(machine));
        output.selectOutputStream(3, false);
    }
}
