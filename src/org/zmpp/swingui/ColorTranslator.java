/*
 * $Id: ColorTranslator.java 535 2008-02-19 06:02:50Z weiju $
 * 
 * Created on 2006/02/24
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

import java.awt.Color;

/**
 * This class translates color numbers into Java AWT Color objects. It was
 * outfactored from the Viewport because it is common behaviour which is shared
 * between model 6 and non-model 6 screen models.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class ColorTranslator {

    public static final int UNDEFINED = -1000;
    public static final int COLOR_UNDER_CURSOR = -1;
    public static final int COLOR_CURRENT = 0;
    public static final int COLOR_DEFAULT = 1;
    public static final int COLOR_BLACK = 2;
    public static final int COLOR_RED = 3;
    public static final int COLOR_GREEN = 4;
    public static final int COLOR_YELLOW = 5;
    public static final int COLOR_BLUE = 6;
    public static final int COLOR_MAGENTA = 7;
    public static final int COLOR_CYAN = 8;
    public static final int COLOR_WHITE = 9;
    public static final int COLOR_MS_DOS_DARKISH_GREY = 10;

    private static final Color GREEN = new Color(0, 190, 0);
    private static final Color RED = new Color(190, 0, 0);
    private static final Color YELLOW = new Color(190, 190, 0);
    private static final Color BLUE = new Color(0, 0, 190);
    private static final Color MAGENTA = new Color(190, 0, 190);
    private static final Color CYAN = new Color(0, 190, 190);

    private static ColorTranslator instance = new ColorTranslator();

    /**
     * Constructor.
     */
    private ColorTranslator() {
    }

    /**
     * Returns the singleton instance.
     *
     * @return the singleton instance
     */
    public static ColorTranslator getInstance() {
        return instance;
    }

    /**
     * Translates the specified color number.
     *
     * @param colornum the color number
     * @param defaultColor the default color
     * @return the color for the number
     */
    public Color translate(int colornum, int defaultColor) {

        switch (colornum) {

            case COLOR_DEFAULT:
                return translate(defaultColor, UNDEFINED);
            case COLOR_BLACK:
                return Color.BLACK;
            case COLOR_RED:
                return RED;
            case COLOR_GREEN:
                return GREEN;
            case COLOR_YELLOW:
                return YELLOW;
            case COLOR_BLUE:
                return BLUE;
            case COLOR_MAGENTA:
                return MAGENTA;
            case COLOR_CYAN:
                return CYAN;
            case COLOR_WHITE:
                return Color.WHITE;
            case COLOR_MS_DOS_DARKISH_GREY:
                return Color.DARK_GRAY;
        }
        return Color.BLACK;
    }

    /**
     * Translates the specified color number to a Color object.
     *
     * @param colornum the color number
     * @return the color
     */
    public Color translate(int colornum) {

        return translate(colornum, UNDEFINED);
    }
}
