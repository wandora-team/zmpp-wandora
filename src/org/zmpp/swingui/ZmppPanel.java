/*
 * ZmppPanel
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.zmpp.io.IOSystem;
import org.zmpp.io.InputStream;
import org.zmpp.media.Resources;
import org.zmpp.media.StoryMetadata;
import org.zmpp.vm.Machine;
import org.zmpp.vm.ScreenModel;
import org.zmpp.vm.StatusLine;

/**
 *
 * @author akivela
 */
public class ZmppPanel extends JPanel implements InputStream, StatusLine, IOSystem {

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
    private DisplaySettings settings;
    private Preferences preferences;

    /**
     * Constructor.
     *
     * @param machine a Machine object
     */
    public ZmppPanel(final Machine machine) {
        super();
        this.machine = machine;
        lineEditor = new LineEditorImpl(machine.getGameData().getStoryFileHeader(),
                machine.getGameData().getZsciiEncoding());

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
            this.add(statusPanel, BorderLayout.NORTH);
            this.add(view, BorderLayout.CENTER);

        } else {
            this.add(view, BorderLayout.CENTER);
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

        }
    }

    public void setTitle(String title) {
        System.out.println("title: " + title);
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
    // *************************************************************************
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
    // *************************************************************************
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
    // *************************************************************************
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

    public void about(JFrame parent) {
        JOptionPane.showMessageDialog(parent,
                Main.APPNAME + getMessage("dialog.about.msg"),
                getMessage("dialog.about.title"),
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void quit() {
        // Can't quit panel
    }

    public void aboutGame(JFrame parent) {
        GameInfoDialog dialog = new GameInfoDialog(parent,
                machine.getGameData().getResources());
        dialog.setVisible(true);
    }

    public void editPreferences(JFrame parent) {
        PreferencesDialog dialog = new PreferencesDialog(parent, preferences,
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
