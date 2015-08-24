/*
 * $Id: AppletMachineFactory.java 562 2008-04-18 17:37:55Z weiju $
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
package org.zmpp.swingui;

import java.net.URL;

import org.zmpp.io.IOSystem;
import org.zmpp.io.InputStream;
import org.zmpp.vm.Machine;
import org.zmpp.vm.MachineFactory;
import org.zmpp.vm.SaveGameDataStore;
import org.zmpp.vm.ScreenModel;
import org.zmpp.vm.StatusLine;

/**
 * This class implements machine creation for an applet.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class AppletMachineFactory extends MachineFactory<ZmppApplet> {

    private ZmppApplet applet;
    private SaveGameDataStore savegamestore;

    /**
     * Constructor.
     *
     * @param applet the applet object
     * @param settings the display settings
     * @param savetofile true if should save to file
     * @throws Exception if an error occurs
     */
    public AppletMachineFactory(ZmppApplet applet, URL storyurl, URL resourceurl,
            boolean savetofile)
            throws Exception {
        super(storyurl, resourceurl);
        this.applet = applet;
        savegamestore = savetofile ? new FileSaveGameDataStore(applet)
                : new MemorySaveGameDataStore();
    }

    public AppletMachineFactory(ZmppApplet applet, URL zblorburl,
            boolean savetofile)
            throws Exception {
        super(null, zblorburl);
        this.applet = applet;
        savegamestore = savetofile ? new FileSaveGameDataStore(applet)
                : new MemorySaveGameDataStore();
    }

    /**
     * {@inheritDoc}
     */
    protected void reportInvalidStory() {
        System.err.printf("invalid story file");
    }

    /**
     * {@inheritDoc}
     */
    protected IOSystem getIOSystem() {
        return applet;
    }

    /**
     * {@inheritDoc}
     */
    protected InputStream getKeyboardInputStream() {
        return applet;
    }

    /**
     * {@inheritDoc}
     */
    protected StatusLine getStatusLine() {
        return applet;
    }

    /**
     * {@inheritDoc}
     */
    protected ScreenModel getScreenModel() {
        return applet.getScreenModel();
    }

    /**
     * {@inheritDoc}
     */
    protected SaveGameDataStore getSaveGameDataStore() {
        return savegamestore;
    }

    /**
     * {@inheritDoc}
     */
    protected ZmppApplet initUI(Machine machine) {

        applet.initUI(machine);
        return applet;
    }

    /**
     * {@inheritDoc}
     */
    public ZmppApplet getUI() {
        return applet;
    }
}
