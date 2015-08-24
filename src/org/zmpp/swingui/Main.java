/*
 * $Id: Main.java 562 2008-04-18 17:37:55Z weiju $
 * 
 * Created on 2005/10/17
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.PropertyResourceBundle;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * This class starts the ZMPP swing interface.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class Main {

    private static PropertyResourceBundle MESSAGE_BUNDLE
            = (PropertyResourceBundle) PropertyResourceBundle.getBundle("zmpp_messages");

    /**
     * The application name.
     */
    public static final String APPNAME = getMessage("app.name");
    public static boolean DEBUG = false;

    /**
     * Global function to return the message string.
     *
     * @param property the property name
     * @return the message
     */
    public static String getMessage(String property) {
        return MESSAGE_BUNDLE.getString(property);
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        System.setProperty("swing.aatext", "true");
        DEBUG = "true".equals(System.getProperty("DEBUG"));
        setMacOsXProperties();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (args.length >= 1) {
            runWithParameters(args);
        } else {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        JFileChooser fileChooser
                                = new JFileChooser(System.getProperty("user.dir"));
                        fileChooser.setDialogTitle(getMessage("dialog.open.msg"));
                        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                            final File storyfile = fileChooser.getSelectedFile();
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    runStoryFile(storyfile);
                                }
                            });
                        }
                    }
                });
            } catch (Exception ignore) {
            }
        }
    }

    public static boolean isMacOsX() {
        return System.getProperty("mrj.version") != null;
    }

    private static void setMacOsXProperties() {
        if (isMacOsX()) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.eawt.CocoaComponent.CompatibilityMode",
                    "false");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name",
                    "ZMPP");
        }
    }

    private static void runWithParameters(String[] args) {
        if (isFile(args[0])) {
            runStoryFile(new File(args[0]));
        } else if (isUrl(args[0])) {
            try {
                runStoryUrl(new URL(args[0]));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    MessageFormat.format(getMessage("error.open.msg"), ""),
                    getMessage("error.open.title"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void runStoryUrl(URL url) {
        runStory(createFactoryFromUrl(url));
    }

    private static ApplicationMachineFactory createFactoryFromUrl(URL url) {
        if (url.getPath().endsWith("zblorb") || url.getPath().endsWith("zblb")) {
            return new ApplicationMachineFactory(null, url);
        }
        return new ApplicationMachineFactory(url, null);
    }

    private static void runStoryFile(File file) {
        runStory(createFactoryFromFile(file));
    }

    private static ApplicationMachineFactory createFactoryFromFile(File file) {
        if (isZblorbSuffix(file.getName())) {
            return new ApplicationMachineFactory(file);
        } else {
            File blorbfile = searchForResources(file);
            return new ApplicationMachineFactory(file, blorbfile);
        }
    }

    private static boolean isFile(String str) {
        File file = new File(str);
        return file.exists() && file.isFile();
    }

    private static boolean isUrl(String str) {
        try {
            new URL(str);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * This method opens a frame and runs the specified story file.
     *
     * @param storyfile the story file
     */
    private static void runStory(ApplicationMachineFactory factory) {
        try {
            factory.buildMachine();
            ZmppFrame frame = factory.getUI();
            frame.startMachine();
            frame.pack();
            frame.setVisible(true);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    MessageFormat.format(getMessage("error.open.msg"), ex.getMessage()),
                    getMessage("error.open.title"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean isZblorbSuffix(String filename) {
        return filename.endsWith("zblorb") || filename.endsWith("zlb");
    }

    /**
     * Trys to find a resource file in Blorb format.
     *
     * @param storyfile the storyfile
     * @return the blorb file if one exists or null
     */
    private static File searchForResources(File storyfile) {
        StringTokenizer tok = new StringTokenizer(storyfile.getName(), ".");
        String prefix = tok.nextToken();
        String dir = storyfile.getParent();
        String blorbpath1 = ((dir != null) ? dir + System.getProperty("file.separator") : "")
                + prefix + ".blb";
        String blorbpath2 = ((dir != null) ? dir + System.getProperty("file.separator") : "")
                + prefix + ".blorb";
        File blorbfile1 = new File(blorbpath1);
        System.out.printf("does '%s' exist ? -> %b\n", blorbfile1.getPath(),
                blorbfile1.exists());
        if (blorbfile1.exists()) {
            return blorbfile1;
        }
        File blorbfile2 = new File(blorbpath2);
        System.out.printf("does '%s' exist ? -> %b\n", blorbfile2.getPath(),
                blorbfile2.exists());
        if (blorbfile2.exists()) {
            return blorbfile2;
        }
        return null;
    }
}
