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

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;

/**
 * <p>ParagraphRenderState class.</p>
 */
public class ParagraphRenderState extends AbstractMarginRenderState {
	/**
	 * <p>Constructor for ParagraphRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public ParagraphRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	/** {@inheritDoc} */
	@Override
	protected HtmlInsets getDefaultMarginInsets() {
		final HtmlInsets insets = new HtmlInsets();
		final int topBottom = HtmlValues.getPixelSize("1rem", null, element.getDocumentNode().getDefaultView(), -1);
		insets.top = topBottom;
		insets.bottom = topBottom;
		insets.topType = HtmlInsets.TYPE_PIXELS;
		insets.bottomType = HtmlInsets.TYPE_PIXELS;
		return insets;
	}

}
