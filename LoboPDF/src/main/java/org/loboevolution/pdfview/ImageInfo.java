/*
 * Copyright 2004 Sun Microsystems, Inc., 4150 Network Circle,
 * Santa Clara, California 95054, U.S.A. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.loboevolution.pdfview;

import com.mercuryred.render.interfaces.ui.Color;
import com.mercuryred.render.interfaces.ui.geom.Rectangle2D;

/**
 * <p>ImageInfo class.</p>
 *
  *
  *
 */
public class ImageInfo {

    final int width;
    final int height;
    final Rectangle2D clip;
    Color bgColor;

    /**
     * <p>Constructor for ImageInfo.</p>
     *
     * @param width a int.
     * @param height a int.
     * @param clip a {@link com.mercuryred.render.interfaces.ui.geom.Rectangle2D} object.
     */
    public ImageInfo(int width, int height, Rectangle2D clip) {
        this(width, height, clip, Color.WHITE);
    }

    /**
     * <p>Constructor for ImageInfo.</p>
     *
     * @param width a int.
     * @param height a int.
     * @param clip a {@link com.mercuryred.render.interfaces.ui.geom.Rectangle2D} object.
     * @param bgColor a {@link com.mercuryred.render.interfaces.ui.color} object.
     */
    public ImageInfo(int width, int height, Rectangle2D clip, Color bgColor) {
        this.width = width;
        this.height = height;
        this.clip = clip;
        this.bgColor = bgColor;
    }

    // a hashcode that uses width, height and clip to generate its number
    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int code = (this.width ^ this.height << 16);

        if (this.clip != null) {
            code ^= ((int) this.clip.getWidth() | (int) this.clip.getHeight()) << 8;
            code ^= ((int) this.clip.getMinX() | (int) this.clip.getMinY());
        }

        return code;
    }

    // an equals method that compares values
    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ImageInfo)) {
            return false;
        }

        ImageInfo ii = (ImageInfo) o;

        if (this.width != ii.width || this.height != ii.height) {
            return false;
        } else if (this.clip != null && ii.clip != null) {
            return this.clip.equals(ii.clip);
        } else return this.clip == null && ii.clip == null;
    }
}
