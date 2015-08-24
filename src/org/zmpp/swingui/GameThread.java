/*
 * $Id: GameThread.java 548 2008-03-15 07:18:10Z weiju $
 * 
 * Created on 2005/11/15
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

import java.awt.EventQueue;

import javax.swing.JComponent;

import org.zmpp.vm.Instruction;
import org.zmpp.vm.Machine;
import org.zmpp.vm.ScreenModel;

public class GameThread extends Thread {

    private ScreenModel screen;
    private Machine machine;

    /**
     * Constructor.
     *
     * @param machine a Machine object
     * @param viewport a ScreenModel object
     */
    public GameThread(Machine machine, ScreenModel viewport) {
        this.machine = machine;
        this.screen = viewport;
    }

    public void run() {
        screen.waitInitialized();
        machine.start();

    // on MacOS X, after running the thread keyboard input is suspended
        // for some reason until you either change to another application and
        // back or explicitly request the focus, therefore, do it here, it
        // does no harm on other platforms
        try {
            EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    ((JComponent) screen).requestFocusInWindow();
                }
            });
        } catch (Exception ex) {
        }

        int line = 1;
        while (machine.getCpu().isRunning()) {
            Instruction instr = machine.getCpu().nextStep();
            if (Main.DEBUG) {
                System.out.println(String.format("%04d - %05x: %s", line,
                        machine.getCpu().getProgramCounter(), instr.toString()));
            }
            instr.execute();
            line++;
        }
    }
}
