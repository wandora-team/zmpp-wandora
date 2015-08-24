/*
 * $Id: BlorbImages.java 536 2008-02-19 06:03:27Z weiju $
 * 
 * Created on 2006/02/06
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
package org.zmpp.blorb;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.zmpp.base.Memory;
import org.zmpp.blorb.BlorbImage.Ratio;
import org.zmpp.blorb.BlorbImage.Resolution;
import org.zmpp.blorb.BlorbImage.ResolutionInfo;
import org.zmpp.blorb.BlorbImage.ScaleInfo;
import org.zmpp.iff.Chunk;
import org.zmpp.iff.FormChunk;

/**
 * This class implements the Image collection.
 *
 * @author Wei-ju Wu
 * @version 1.0
 */
public class BlorbImages extends BlorbMediaCollection<BlorbImage> {

    /**
     * This map implements the image database.
     */
    private Map<Integer, BlorbImage> images;

    /**
     * Constructor.
     *
     * @param formchunk the form chunk
     */
    public BlorbImages(FormChunk formchunk) {

        super(formchunk);
        handleResoChunk();
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {

        super.clear();
        images.clear();
    }

    /**
     * {@inheritDoc}
     */
    protected void initDatabase() {
        images = new HashMap<Integer, BlorbImage>();
    }

    /**
     * {@inheritDoc}
     */
    protected boolean isHandledResource(final byte[] usageId) {
        return usageId[0] == 'P' && usageId[1] == 'i' && usageId[2] == 'c'
                && usageId[3] == 't';
    }

    /**
     * {@inheritDoc}
     */
    public BlorbImage getResource(final int resourcenumber) {
        return images.get(resourcenumber);
    }

    /**
     * {@inheritDoc}
     */
    protected boolean putToDatabase(final Chunk chunk, final int resnum) {
        if (!handlePlaceholder(chunk, resnum)) {
            return handlePicture(chunk, resnum);
        }
        return true;
    }

    private boolean handlePlaceholder(final Chunk chunk, final int resnum) {

        if ("Rect".equals(new String(chunk.getId()))) {

            // Place holder
            Memory memory = chunk.getMemory();
            int width = (int) memory.readUnsigned32(Chunk.CHUNK_HEADER_LENGTH);
            int height = (int) memory.readUnsigned32(Chunk.CHUNK_HEADER_LENGTH + 4);
            images.put(resnum, new BlorbImage(width, height));

            return true;
        }
        return false;
    }

    private boolean handlePicture(final Chunk chunk, final int resnum) {

        final InputStream is = new MemoryInputStream(chunk.getMemory(),
                Chunk.CHUNK_HEADER_LENGTH, chunk.getSize() + Chunk.CHUNK_HEADER_LENGTH);
        try {

            final BufferedImage img = ImageIO.read(is);
            images.put(resnum, new BlorbImage(img));
            return true;

        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return false;
    }

    private void handleResoChunk() {

        Chunk resochunk = getFormChunk().getSubChunk("Reso".getBytes());
        if (resochunk != null) {

            adjustResolution(resochunk);
        }
    }

    private void adjustResolution(Chunk resochunk) {

        Memory memory = resochunk.getMemory();
        int offset = Chunk.CHUNK_ID_LENGTH;
        int size = (int) memory.readUnsigned32(offset);
        offset += Chunk.CHUNK_SIZEWORD_LENGTH;
        int px = (int) memory.readUnsigned32(offset);
        offset += 4;
        int py = (int) memory.readUnsigned32(offset);
        offset += 4;
        int minx = (int) memory.readUnsigned32(offset);
        offset += 4;
        int miny = (int) memory.readUnsigned32(offset);
        offset += 4;
        int maxx = (int) memory.readUnsigned32(offset);
        offset += 4;
        int maxy = (int) memory.readUnsigned32(offset);
        offset += 4;

        ResolutionInfo resinfo = new ResolutionInfo(new Resolution(px, py),
                new Resolution(minx, miny), new Resolution(maxx, maxy));

        for (int i = 0; i < getNumResources(); i++) {

            if (offset >= size) {
                break;
            }
            int imgnum = (int) memory.readUnsigned32(offset);
            offset += 4;
            int ratnum = (int) memory.readUnsigned32(offset);
            offset += 4;
            int ratden = (int) memory.readUnsigned32(offset);
            offset += 4;
            int minnum = (int) memory.readUnsigned32(offset);
            offset += 4;
            int minden = (int) memory.readUnsigned32(offset);
            offset += 4;
            int maxnum = (int) memory.readUnsigned32(offset);
            offset += 4;
            int maxden = (int) memory.readUnsigned32(offset);
            offset += 4;
            ScaleInfo scaleinfo = new ScaleInfo(resinfo, new Ratio(ratnum, ratden),
                    new Ratio(minnum, minden), new Ratio(maxnum, maxden));
            BlorbImage img = images.get(imgnum);

            if (img != null) {

                img.setScaleInfo(scaleinfo);
            }
        }
    }
}
