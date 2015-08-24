/*
 * $Id: UnpredictableRandomGenerator.java 536 2008-02-19 06:03:27Z weiju $
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
package org.zmpp.vmutil;

import java.util.Random;

/**
 * This class implements the "random" random number generator mentioned in the
 * Z-machine standard document.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class UnpredictableRandomGenerator implements RandomGenerator {

    private Random rand;

    /**
     * Constructor.
     */
    public UnpredictableRandomGenerator() {
        rand = new Random(System.currentTimeMillis());
    }

    /**
     * {@inheritDoc}
     */
    public int next() {
        return rand.nextInt(MAX_VALUE - 1) + 1;
    }
}
