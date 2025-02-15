/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.smil;

import com.gargoylesoftware.css.dom.DOMException;

/**
 * This interface is used by SMIL elements root-layout, top-layout and region.
 */
public interface ElementLayout {
    /**
     * <p>getTitle.</p>
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getTitle();

    /**
     * <p>setTitle.</p>
     *
     * @param title a {@link java.lang.String} object.
     * @throws com.gargoylesoftware.css.dom.DOMException if any.
     */
    void setTitle(String title) throws DOMException;

    /**
     * <p>getBackgroundColor.</p>
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a {@link java.lang.String} object.
     */
    String getBackgroundColor();

    /**
     * <p>setBackgroundColor.</p>
     *
     * @param backgroundColor a {@link java.lang.String} object.
     * @throws com.gargoylesoftware.css.dom.DOMException if any.
     */
    void setBackgroundColor(String backgroundColor) throws DOMException;

    /**
     * <p>getHeight.</p>
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a int.
     */
    int getHeight();

    /**
     * <p>setHeight.</p>
     *
     * @param height a int.
     * @throws com.gargoylesoftware.css.dom.DOMException if any.
     */
    void setHeight(int height) throws DOMException;

    /**
     * <p>getWidth.</p>
     *
     * @exception DOMException
     *                NO_MODIFICATION_ALLOWED_ERR: Raised if this attribute is
     *                readonly.
     * @return a int.
     */
    int getWidth();

    /**
     * <p>setWidth.</p>
     *
     * @param width a int.
     * @throws com.gargoylesoftware.css.dom.DOMException if any.
     */
    void setWidth(final int width) throws DOMException;

}
