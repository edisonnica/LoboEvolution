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

package org.loboevolution.html.renderstate;

import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.FontValues;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;

import java.awt.*;

/**
 * <p>SmallRenderState class.</p>
 */
public class SmallRenderState extends RenderStateDelegator {

    private final HTMLElementImpl element;

    private final RenderState prevRenderState;

    /**
     * <p>Constructor for SmallRenderState.</p>
     *
     * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
     * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
     */
    public SmallRenderState(RenderState prevRenderState, HTMLElementImpl element) {
        super(prevRenderState);
        this.element = element;
        this.prevRenderState = prevRenderState;
    }


    @Override
    public Font getFont() {
        final AbstractCSSProperties props = element.getCurrentStyle();
        final String fontSize = props == null ? null : props.getFontSize();
        FontKey key = FontValues.getDefaultFontKey();
        final String fSize = Strings.isNotBlank(fontSize) ? fontSize : CSSValues.SMALLER.getValue();
        key.setFontSize(FontValues.getFontSize(fSize, element.getDocumentNode().getDefaultView(), prevRenderState));
        return FontFactory.getInstance().getFont(FontValues.getFontKey(key, element, props, prevRenderState));
    }

    @Override
    public int getDisplay() {
        return DISPLAY_INLINE;
    }
}
