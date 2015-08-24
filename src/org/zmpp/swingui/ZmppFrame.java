/*
 * $Id: ZmppFrame.java 548 2008-03-15 07:18:10Z weiju $
 * 
 * Created on 2005/10/19
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.prefs.Preferences;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.zmpp.io.IOSystem;
import org.zmpp.io.InputStream;
import org.zmpp.media.Resources;
import org.zmpp.media.StoryMetadata;
import org.zmpp.vm.Machine;
import org.zmpp.vm.ScreenModel;
import org.zmpp.vm.StatusLine;

import apple.dts.osxadapter.OSXAdapter;

/**
 * This class is the main frame for ZMPP run as an application. 
 * 
 * @author Wei-ju Wu
 * @version 1.0
 */
public class ZmppFrame extends JFrame implements InputStream, StatusLine, IOSystem {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    private JLabel global1ObjectLabel;
    private JLabel statusLabel;
    private ScreenModel screen;
    private Machine machine;
    private LineEditorImpl lineEditor;
    private GameThread currentGame;
    private boolean isMacOs;
    private DisplaySettings settings;
    private Preferences preferences;

    /**
     * Constructor.
     * 
     * @param machine a Machine object
     */
    public ZmppFrame(final Machine machine) {
        super(Main.APPNAME);
        this.machine = machine;
        lineEditor = new LineEditorImpl(machine.getGameData().getStoryFileHeader(),
                machine.getGameData().getZsciiEncoding());

        isMacOs = Main.isMacOsX();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        JComponent view = null;

        preferences = Preferences.userNodeForPackage(ZmppFrame.class);
        settings = createDisplaySettings(preferences);

        if (machine.getGameData().getStoryFileHeader().getVersion() == 6) {
            view = new Viewport6(machine, lineEditor, settings);
            screen = (ScreenModel) view;
        } else {
            view = new TextViewport(machine, lineEditor, settings);
            screen = (ScreenModel) view;
        }
        view.setPreferredSize(new Dimension(640, 476));
        view.setMinimumSize(new Dimension(400, 300));

        if (machine.getGameData().getStoryFileHeader().getVersion() <= 3) {
            JPanel statusPanel = new JPanel(new GridLayout(1, 2));
            JPanel status1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel status2Panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            statusPanel.add(status1Panel);
            statusPanel.add(status2Panel);

            global1ObjectLabel = new JLabel(" ");
            statusLabel = new JLabel(" ");
            status1Panel.add(global1ObjectLabel);
            status2Panel.add(statusLabel);
            getContentPane().add(statusPanel, BorderLayout.NORTH);
            getContentPane().add(view, BorderLayout.CENTER);

        } else {
            setContentPane(view);
        }

        JMenuBar menubar = new JMenuBar();
        setJMenuBar(menubar);
        JMenu helpMenu = null;

    // Menus need to be slightly different on MacOS X, they do not have
        // an explicit File menu
        if (!isMacOs) {

            JMenu fileMenu = new JMenu(getMessage("menu.file.name"));
            fileMenu.setMnemonic(getMessage("menu.file.mnemonic").charAt(0));
            menubar.add(fileMenu);

            // Quit is already in the application menu
            JMenuItem exitItem = new JMenuItem(getMessage("menu.file.quit.name"));
            exitItem.setMnemonic(getMessage("menu.file.quit.mnemonic").charAt(0));
            fileMenu.add(exitItem);
            exitItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    quit();
                }
            });
            JMenu editMenu = new JMenu(getMessage("menu.edit.name"));
            menubar.add(editMenu);
            editMenu.setMnemonic(getMessage("menu.edit.mnemonic").charAt(0));
            JMenuItem preferencesItem
                    = new JMenuItem(getMessage("menu.edit.prefs.name"));
            preferencesItem.setMnemonic(getMessage("menu.edit.prefs.mnemonic")
                    .charAt(0));
            editMenu.add(preferencesItem);
            preferencesItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    editPreferences();
                }
            });

            helpMenu = new JMenu(getMessage("menu.help.name"));
            menubar.add(helpMenu);
            helpMenu.setMnemonic(getMessage("menu.help.mnemonic").charAt(0));

            JMenuItem aboutItem = new JMenuItem(getMessage("menu.help.about.name"));
            aboutItem.setMnemonic(getMessage("menu.help.about.mnemonic")
                    .charAt(0));
            helpMenu.add(aboutItem);
            aboutItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    about();
                }
            });
        } else {
            try {
                OSXAdapter.setAboutHandler(this,
                        ZmppFrame.class.getDeclaredMethod("about"));
                OSXAdapter.setQuitHandler(this,
                        ZmppFrame.class.getDeclaredMethod("quit"));
                OSXAdapter.setPreferencesHandler(this,
                        ZmppFrame.class.getDeclaredMethod("editPreferences"));
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
        }

        //addKeyListener(lineEditor);
        view.addKeyListener(lineEditor);
        view.addMouseListener(lineEditor);

        // just for debugging
        view.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                //System.out.printf("mouse pos: %d %d\n", e.getX(), e.getY());
            }
        });

        // Add an info dialog and a title if metadata exists
        Resources resources = machine.getGameData().getResources();
        if (resources != null && resources.getMetadata() != null) {

            StoryMetadata storyinfo = resources.getMetadata().getStoryInfo();
            setTitle(Main.APPNAME + " - " + storyinfo.getTitle()
                    + " (" + storyinfo.getAuthor() + ")");
            if (isMacOs) {
                helpMenu = new JMenu(getMessage("menu.help.name"));
                menubar.add(helpMenu);
            }
            JMenuItem aboutGameItem
                    = new JMenuItem(getMessage("menu.help.aboutgame.name"));
            helpMenu.add(aboutGameItem);
            aboutGameItem.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    aboutGame();
                }
            });
        }
    }

    /**
     * Access to screen model.
     *
     * @return the screen model
     */
    public ScreenModel getScreenModel() {
        return screen;
    }

    public void startMachine() {
        currentGame = new GameThread(machine, screen);
        currentGame.start();
    }

  // *************************************************************************
    // ******** StatusLine interface
    // ******************************************
    public void updateStatusScore(final String objectName, final int score,
            final int steps) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                global1ObjectLabel.setText(objectName);
                statusLabel.setText(score + "/" + steps);
            }
        });
    }

    public void updateStatusTime(final String objectName, final int hours,
            final int minutes) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                global1ObjectLabel.setText(objectName);
                statusLabel.setText(String.format("%02d:%02d", hours, minutes));
            }
        });
    }

  // *************************************************************************
    // ******** IOSystem interface
    // ******************************************
    public Writer getTranscriptWriter() {
        File currentdir = new File(System.getProperty("user.dir"));
        JFileChooser fileChooser = new JFileChooser(currentdir);
        fileChooser.setDialogTitle(getMessage("dialog.settranscript.title"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                return new FileWriter(fileChooser.getSelectedFile());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public Reader getInputStreamReader() {
        File currentdir = new File(System.getProperty("user.dir"));
        JFileChooser fileChooser = new JFileChooser(currentdir);
        fileChooser.setDialogTitle(getMessage("dialog.setinput.title"));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                return new FileReader(fileChooser.getSelectedFile());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

  // *************************************************************************
    // ******** InputStream interface
    // ******************************************
    public void close() {
    }

    public void cancelInput() {
        lineEditor.cancelInput();
    }

    /**
     * {@inheritDoc}
     */
    public char getZsciiChar(boolean flushBeforeGet) {
        enterEditMode(flushBeforeGet);
        char zsciiChar = lineEditor.nextZsciiChar();
        leaveEditMode(flushBeforeGet);
        return zsciiChar;
    }

    private void enterEditMode(boolean flushbuffer) {
        if (!lineEditor.isInputMode()) {
            screen.resetPagers();
            lineEditor.setInputMode(true, flushbuffer);
        }
    }

    private void leaveEditMode(boolean flushbuffer) {
        lineEditor.setInputMode(false, flushbuffer);
    }

    public void about() {
        JOptionPane.showMessageDialog(this,
                Main.APPNAME + getMessage("dialog.about.msg"),
                getMessage("dialog.about.title"),
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void quit() {
        System.exit(0);
    }

    private void aboutGame() {
        GameInfoDialog dialog = new GameInfoDialog(this,
                machine.getGameData().getResources());
        dialog.setVisible(true);
    }

    public void editPreferences() {
        PreferencesDialog dialog = new PreferencesDialog(this, preferences,
                settings);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private DisplaySettings createDisplaySettings(Preferences preferences) {
        int stdfontsize = preferences.getInt("stdfontsize", 12);
        int fixedfontsize = preferences.getInt("fixedfontsize", 12);
        int defaultforeground = preferences.getInt("defaultforeground",
                ColorTranslator.UNDEFINED);
        int defaultbackground = preferences.getInt("defaultbackground",
                ColorTranslator.UNDEFINED);
        boolean antialias = preferences.getBoolean("antialias", true);

        return new DisplaySettings(stdfontsize, fixedfontsize, defaultbackground,
                defaultforeground, antialias);
    }

    private String getMessage(String key) {
        return Main.getMessage(key);
    }
}
