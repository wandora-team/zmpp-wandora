/*
 * PanelMachineFactory
 * 
 * Created on 2015/08/24
 * Copyright 2005-2015 by Wandora Team
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.JOptionPane;
import org.zmpp.blorb.BlorbResources;
import org.zmpp.blorb.BlorbStory;
import org.zmpp.iff.DefaultFormChunk;
import org.zmpp.iff.FormChunk;
import org.zmpp.io.IOSystem;
import org.zmpp.io.InputStream;
import org.zmpp.media.Resources;
import org.zmpp.vm.Machine;
import org.zmpp.vm.MachineFactory;
import org.zmpp.vm.SaveGameDataStore;
import org.zmpp.vm.ScreenModel;
import org.zmpp.vm.StatusLine;
import org.zmpp.vmutil.FileUtils;

/**
 *
 * @author akivela
 */

public class PanelMachineFactory extends MachineFactory<ZmppPanel> {


    private ZmppPanel zmppPanel;
    private SaveGameDataStore savegamestore;

    public PanelMachineFactory(File storyfile, File blorbfile) {
        super(storyfile, blorbfile);
    }

    public PanelMachineFactory(File blorbfile) {
        super(blorbfile);
    }

    public PanelMachineFactory(URL storyurl, URL blorburl) {
        super(storyurl, blorburl);
    }
    
    public PanelMachineFactory(byte[] blorbdata) {
        super(blorbdata);
    }

    public PanelMachineFactory(byte[] storydata, byte[] blorbdata) {
        super(storydata, blorbdata);
    }
    
  
    /**
     * {@inheritDoc}
     */
    protected void reportInvalidStory() {

        JOptionPane.showMessageDialog(null,
            "Invalid story file.",
            "Story file read error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }

    /**
     * {@inheritDoc}
     */
    protected ZmppPanel initUI(Machine machine) {

      zmppPanel = new ZmppPanel(machine);
      savegamestore = new FileSaveGameDataStore(zmppPanel);
      return zmppPanel;
    }

    /**
     * {@inheritDoc}
     */
    public ZmppPanel getUI() { return zmppPanel; }

    /**
     * {@inheritDoc}
     */
    protected IOSystem getIOSystem() { return zmppPanel; }  

    /**
     * {@inheritDoc}
     */
    protected InputStream getKeyboardInputStream() { return zmppPanel; }

    /**
     * {@inheritDoc}
     */
    protected StatusLine getStatusLine() { return zmppPanel; }

    /**
     * {@inheritDoc}
     */
    protected ScreenModel getScreenModel() { return zmppPanel.getScreenModel(); }

    /**
     * {@inheritDoc}
     */
    protected SaveGameDataStore getSaveGameDataStore() { return savegamestore; }
}

    
