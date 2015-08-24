/*
 * $Id: FileSaveGameDataStore.java 548 2008-03-15 07:18:10Z weiju $
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

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JFileChooser;

import org.zmpp.base.DefaultMemory;
import org.zmpp.iff.DefaultFormChunk;
import org.zmpp.iff.FormChunk;
import org.zmpp.iff.WritableFormChunk;
import org.zmpp.vm.SaveGameDataStore;

/**
 * This class saves game states into the file system.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class FileSaveGameDataStore implements SaveGameDataStore {

    private Component parent;

    /**
     * Constructor.
     *
     * @param parent the parent component for the file dialog
     */
    public FileSaveGameDataStore(Component parent) {

        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     */
    public boolean saveFormChunk(WritableFormChunk formchunk) {

        File currentdir = new File(System.getProperty("user.dir"));
        JFileChooser fileChooser = new JFileChooser(currentdir);
        fileChooser.setDialogTitle(Main.getMessage("dialog.savegame.title"));

        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {

            File savefile = fileChooser.getSelectedFile();
            RandomAccessFile raf = null;
            try {

                raf = new RandomAccessFile(savefile, "rw");
                byte[] data = formchunk.getBytes();
                raf.write(data);
                return true;

            } catch (IOException ex) {

                ex.printStackTrace();

            } finally {

                if (raf != null) {
                    try {
                        raf.close();
                    } catch (Exception ex) {
                    }
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public FormChunk retrieveFormChunk() {

        File currentdir = new File(System.getProperty("user.dir"));
        JFileChooser fileChooser = new JFileChooser(currentdir);
        fileChooser.setDialogTitle(Main.getMessage("dialog.restoregame.title"));
        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {

            File savefile = fileChooser.getSelectedFile();
            RandomAccessFile raf = null;
            try {

                raf = new RandomAccessFile(savefile, "r");
                byte[] data = new byte[(int) raf.length()];
                raf.readFully(data);
                return new DefaultFormChunk(new DefaultMemory(data));

            } catch (IOException ex) {

                ex.printStackTrace();

            } finally {

                if (raf != null) {
                    try {
                        raf.close();
                    } catch (Exception ex) {
                    }
                }
            }
        }
        return null;
    }
}
