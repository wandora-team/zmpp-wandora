/*
 * $Id: ScreenFont.java 535 2008-02-19 06:02:50Z weiju $
 * 
 * Created on 2006/02/25
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

import java.awt.Font;

/**
 * ScreenFont encapsulates all aspects of a font in the Z-machine, namely the
 * number, the style and the concrete font. This takes further load off the
 * central screen model classes.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class ScreenFont {

    private int number;
    private int style;
    private Font font;
    private boolean reverseVideo;

    /**
     * Constructor.
     *
     * @param font the font
     * @param number the font number
     * @param style the font style
     */
    public ScreenFont(Font font, int number, int style) {
        this(font, number, style, false);
    }

    /**
     * Constructor.
     *
     * @param font the font
     * @param number the font number
     * @param style the font style
     * @param reverseVideo the reverse video flag
     */
    public ScreenFont(Font font, int number, int style, boolean reverseVideo) {
        this.font = font;
        this.number = number;
        this.style = style;
        this.reverseVideo = reverseVideo;
    }

    /**
     * Returns the font.
     *
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * Returns the font number.
     *
     * @return the font number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns the font style the font style.
     *
     * @return the font style
     */
    public int getStyle() {
        return style;
    }

    /**
     * Returns the reverse video flag.
     *
     * @return the reverse video flag
     */
    public boolean isReverseVideo() {
        return reverseVideo;
    }
}
