/*
 * $Id: DefaultStoryFileHeader.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2005/09/23
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

import org.zmpp.base.Memory;

/**
 * This is the default implementation of the StoryFileHeader interface.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class DefaultStoryFileHeader implements StoryFileHeader {

    /**
     * The memory map.
     */
    private Memory memory;

    /**
     * Constructor.
     *
     * @param memory a Memory object
     */
    public DefaultStoryFileHeader(final Memory memory) {
        this.memory = memory;
    }

    /**
     * {@inheritDoc}
     */
    public int getVersion() {
        return memory.readUnsignedByte(0x00);
    }

    /**
     * {@inheritDoc}
     */
    public int getRelease() {
        return memory.readUnsignedShort(0x02);
    }

    /**
     * {@inheritDoc}
     */
    public int getHighMemAddress() {
        return memory.readUnsignedShort(0x04);
    }

    /**
     * {@inheritDoc}
     */
    public int getProgramStart() {
        return memory.readUnsignedShort(0x06);
    }

    /**
     * {@inheritDoc}
     */
    public int getDictionaryAddress() {
        return memory.readUnsignedShort(0x08);
    }

    /**
     * {@inheritDoc}
     */
    public int getObjectTableAddress() {
        return memory.readUnsignedShort(0x0a);
    }

    /**
     * {@inheritDoc}
     */
    public int getGlobalsAddress() {
        return memory.readUnsignedShort(0x0c);
    }

    /**
     * {@inheritDoc}
     */
    public int getStaticsAddress() {
        return memory.readUnsignedShort(0x0e);
    }

    /**
     * {@inheritDoc}
     */
    public String getSerialNumber() {
        return extractAscii(0x12, 6);
    }

    /**
     * {@inheritDoc}
     */
    public int getAbbreviationsAddress() {
        return memory.readUnsignedShort(0x18);
    }

    /**
     * {@inheritDoc}
     */
    public int getFileLength() {
    // depending on the story file version we have to multiply the
        // file length in the header by a constant
        int fileLength = memory.readUnsignedShort(0x1a);
        if (getVersion() <= 3) {

            fileLength *= 2;

        } else if (getVersion() <= 5) {

            fileLength *= 4;

        } else {

            fileLength *= 8;
        }
        return fileLength;
    }

    /**
     * {@inheritDoc}
     */
    public int getChecksum() {
        return memory.readUnsignedShort(0x1c);
    }

    /**
     * {@inheritDoc}
     */
    public void setInterpreterNumber(final int number) {
        memory.writeUnsignedByte(0x1e, (short) number);
    }

    /**
     * {@inheritDoc}
     */
    public int getInterpreterNumber() {
        return memory.readUnsignedByte(0x1e);
    }

    /**
     * {@inheritDoc}
     */
    public void setInterpreterVersion(final int version) {
        if (getVersion() == 4 || getVersion() == 5) {
            memory.writeUnsignedByte(0x1f,
                    (short) String.valueOf(version).charAt(0));
        } else {
            memory.writeUnsignedByte(0x1f, (short) version);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setScreenWidth(final int numChars) {
        memory.writeUnsignedByte(0x21, (short) numChars);
    }

    /**
     * {@inheritDoc}
     */
    public void setScreenWidthUnits(final int units) {
        memory.writeUnsignedShort(0x22, units);
    }

    /**
     * {@inheritDoc}
     */
    public int getScreenWidthUnits() {
        return memory.readUnsignedShort(0x22);
    }

    /**
     * {@inheritDoc}
     */
    public int getScreenWidth() {
        return memory.readUnsignedByte(0x21);
    }

    /**
     * {@inheritDoc}
     */
    public int getScreenHeight() {
        return memory.readUnsignedByte(0x20);
    }

    /**
     * {@inheritDoc}
     */
    public int getScreenHeightUnits() {
        return memory.readUnsignedShort(0x24);
    }

    /**
     * {@inheritDoc}
     */
    public void setScreenHeight(final int numLines) {
        memory.writeUnsignedByte(0x20, (short) numLines);
    }

    /**
     * {@inheritDoc}
     */
    public void setScreenHeightUnits(final int units) {
        memory.writeUnsignedShort(0x24, units);
    }

    /**
     * {@inheritDoc}
     */
    public int getRoutineOffset() {
        return memory.readUnsignedShort(0x28);
    }

    /**
     * {@inheritDoc}
     */
    public int getStaticStringOffset() {
        return memory.readUnsignedShort(0x2a);
    }

    /**
     * {@inheritDoc}
     */
    public int getDefaultBackgroundColor() {
        return memory.readUnsignedByte(0x2c);
    }

    /**
     * {@inheritDoc}
     */
    public int getDefaultForegroundColor() {
        return memory.readUnsignedByte(0x2d);
    }

    /**
     * {@inheritDoc}
     */
    public void setDefaultBackgroundColor(final int color) {
        memory.writeUnsignedByte(0x2c, (short) color);
    }

    /**
     * {@inheritDoc}
     */
    public void setDefaultForegroundColor(final int color) {
        memory.writeUnsignedByte(0x2d, (short) color);
    }

    /**
     * {@inheritDoc}
     */
    public void setStandardRevision(final int major, final int minor) {
        memory.writeUnsignedByte(0x32, (short) major);
        memory.writeUnsignedByte(0x33, (short) minor);
    }

    /**
     * {@inheritDoc}
     */
    public int getTerminatorsAddress() {
        return memory.readUnsignedShort(0x2e);
    }

    /**
     * {@inheritDoc}
     */
    public void setFontWidth(final int units) {
        if (getVersion() == 6) {
            memory.writeUnsignedByte(0x27, (short) units);
        } else {
            memory.writeUnsignedByte(0x26, (short) units);
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getFontWidth() {
        return (getVersion() == 6) ? memory.readUnsignedByte(0x27)
                : memory.readUnsignedByte(0x26);
    }

    /**
     * {@inheritDoc}
     */
    public void setFontHeight(final int units) {
        if (getVersion() == 6) {
            memory.writeUnsignedByte(0x26, (short) units);
        } else {
            memory.writeUnsignedByte(0x27, (short) units);
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getFontHeight() {
        return (getVersion() == 6) ? memory.readUnsignedByte(0x26)
                : memory.readUnsignedByte(0x27);
    }

    /**
     * {@inheritDoc}
     */
    public int getCustomAlphabetTable() {
        return memory.readUnsignedShort(0x34);
    }

    /**
     * {@inheritDoc}
     */
    public void setMouseCoordinates(final int x, final int y) {
        // check the extension table
        final int extTable = memory.readUnsignedShort(0x36);
        if (extTable > 0) {

            final int numwords = memory.readUnsignedShort(extTable);
            if (numwords >= 1) {

                memory.writeUnsignedShort(extTable + 2, x);
            }
            if (numwords >= 2) {

                memory.writeUnsignedShort(extTable + 4, y);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getCustomAccentTable() {
        // check the extension table
        int result = 0;
        final int extTable = memory.readUnsignedShort(0x36);

        if (extTable > 0) {

            final int numwords = memory.readUnsignedShort(extTable);
            if (numwords >= 3) {

                result = memory.readUnsignedShort(extTable + 6);
            }
        }
        return result;
    }

  // ***********************************************************************
    // ****** Attributes
    // **********************************
    /**
     * {@inheritDoc}
     */
    public void setEnabled(final Attribute attribute, final boolean flag) {
        switch (attribute) {

            case DEFAULT_FONT_IS_VARIABLE:
                setDefaultFontIsVariablePitch(flag);
                break;
            case TRANSCRIPTING:
                setTranscripting(flag);
                break;
            case FORCE_FIXED_FONT:
                setForceFixedFont(flag);
                break;
            case SUPPORTS_TIMED_INPUT:
                setTimedInputAvailable(flag);
                break;
            case SUPPORTS_FIXED_FONT:
                setFixedFontAvailable(flag);
                break;
            case SUPPORTS_BOLD:
                setBoldFaceAvailable(flag);
                break;
            case SUPPORTS_ITALIC:
                setItalicAvailable(flag);
                break;
            case SUPPORTS_SCREEN_SPLITTING:
                setScreenSplittingAvailable(flag);
                break;
            case SUPPORTS_STATUSLINE:
                setStatusLineAvailable(flag);
                break;
            case SUPPORTS_COLOURS:
                setSupportsColours(flag);
            default:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEnabled(final Attribute attribute) {
        switch (attribute) {

            case TRANSCRIPTING:
                return isTranscriptingOn();
            case FORCE_FIXED_FONT:
                return forceFixedFont();
            case SCORE_GAME:
                return isScoreGame();
            case DEFAULT_FONT_IS_VARIABLE:
                return defaultFontIsVariablePitch();
            case USE_MOUSE:
                return useMouse();
            default:
                return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setOutputStream3TextWidth(int units) {
        memory.writeUnsignedShort(0x30, units);
    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 55; i++) {

            builder.append(String.format("Addr: %02x Byte: %02x\n", i, memory.readUnsignedByte(i)));
        }
        return builder.toString();
    }

  // ************************************************************************
    // ****** Private section
    // *******************************
    /**
     * Extract an ASCII string of the specified length starting at the specified
     * address.
     *
     * @param address the start address
     * @param length the length of the ASCII string
     * @return the ASCII string at the specified position
     */
    private String extractAscii(final int address, final int length) {
        final StringBuilder builder = new StringBuilder();
        for (int i = address; i < address + length; i++) {

            builder.append((char) memory.readUnsignedByte(i));
        }
        return builder.toString();
    }

    private void setTranscripting(final boolean flag) {
        int flags = memory.readUnsignedByte(0x10);
        flags = flag ? (flags | 1) : (flags & 0xfe);
        memory.writeUnsignedByte(0x10, (short) flags);
    }

    private boolean isTranscriptingOn() {
        return (memory.readUnsignedByte(0x10) & 1) > 0;
    }

    private boolean forceFixedFont() {
        return (memory.readUnsignedByte(0x10) & 2) > 0;
    }

    private void setForceFixedFont(final boolean flag) {
        int flags = memory.readUnsignedByte(0x10);
        flags = flag ? (flags | 2) : (flags & 0xfd);
        memory.writeUnsignedByte(0x10, (short) flags);
    }

    private void setTimedInputAvailable(final boolean flag) {
        int flags = memory.readUnsignedByte(0x01);
        flags = flag ? (flags | 128) : (flags & 0x7f);
        memory.writeUnsignedByte(0x01, (short) flags);
    }

    private boolean isScoreGame() {
        return (memory.readUnsignedByte(0x01) & 2) == 0;
    }

    private void setFixedFontAvailable(final boolean flag) {
        int flags = memory.readUnsignedByte(0x01);
        flags = flag ? (flags | 16) : (flags & 0xef);
        memory.writeUnsignedByte(0x01, (short) flags);
    }

    private void setBoldFaceAvailable(final boolean flag) {
        int flags = memory.readUnsignedByte(0x01);
        flags = flag ? (flags | 4) : (flags & 0xfb);
        memory.writeUnsignedByte(0x01, (short) flags);
    }

    private void setItalicAvailable(final boolean flag) {
        int flags = memory.readUnsignedByte(0x01);
        flags = flag ? (flags | 8) : (flags & 0xf7);
        memory.writeUnsignedByte(0x01, (short) flags);
    }

    private void setScreenSplittingAvailable(final boolean flag) {
        int flags = memory.readUnsignedByte(0x01);
        flags = flag ? (flags | 32) : (flags & 0xdf);
        memory.writeUnsignedByte(0x01, (short) flags);
    }

    private void setStatusLineAvailable(final boolean flag) {
        int flags = memory.readUnsignedByte(0x01);
        flags = flag ? (flags | 16) : (flags & 0xef);
        memory.writeUnsignedByte(0x01, (short) flags);
    }

    private void setDefaultFontIsVariablePitch(final boolean flag) {
        int flags = memory.readUnsignedByte(0x01);
        flags = flag ? (flags | 64) : (flags & 0xbf);
        memory.writeUnsignedByte(0x01, (short) flags);
    }

    private boolean defaultFontIsVariablePitch() {
        return (memory.readUnsignedByte(0x01) & 64) > 0;
    }

    private void setSupportsColours(final boolean flag) {
        int flags = memory.readUnsignedByte(0x01);
        flags = flag ? (flags | 1) : (flags & 0xfe);
        memory.writeUnsignedByte(0x01, (short) flags);
    }

    private boolean useMouse() {
        return (memory.readUnsignedByte(0x10) & 32) > 0;
    }
}
