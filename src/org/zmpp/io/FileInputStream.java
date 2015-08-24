/*
 * $Id: FileInputStream.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 11/08/2005
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
package org.zmpp.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.zmpp.encoding.ZsciiEncoding;

/**
 * This class implements a Z-machine input stream that takes its input from a
 * file. It queries a screen model to provide the input file.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class FileInputStream implements InputStream {

    private IOSystem iosys;
    private ZsciiEncoding encoding;
    private Reader filereader;
    private BufferedReader input;

    /**
     * Constructor.
     *
     * @param iosys an IOSystem object
     * @param encoding a ZSCII encoding object
     */
    public FileInputStream(IOSystem iosys, ZsciiEncoding encoding) {

        this.iosys = iosys;
        this.encoding = encoding;
    }

    public void cancelInput() {

        // file input can not be cancelled at the moment
    }

    /**
     * {@inheritDoc}
     */
    public char getZsciiChar(boolean flushBeforeGet) {

        checkForReader();
        if (input != null) {

            // Read from file
            try {
                if (input.ready()) {
                    final char c = (char) input.read();
                    if (encoding.isConvertableToZscii(c)) {
                        return encoding.getZsciiChar(c);
                    }
                }
            } catch (IOException ex) {

                ex.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    public void close() {

        if (input != null) {

            try {

                input.close();
                input = null;

            } catch (IOException ex) {

                ex.printStackTrace(System.err);
            }
        }

        if (filereader != null) {

            try {

                filereader.close();
                filereader = null;

            } catch (IOException ex) {

                ex.printStackTrace(System.err);
            }
        }
    }

    private void checkForReader() {

        if (filereader == null) {

            filereader = iosys.getInputStreamReader();
            input = new BufferedReader(filereader);
        }
    }
}
