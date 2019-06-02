/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.dom.HTMLHeadingElement;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.ComputedCSS2Properties;
import org.lobobrowser.html.style.HeadingRenderState;
import org.lobobrowser.html.style.RenderState;

public class HTMLHeadingElementImpl extends HTMLAbstractUIElement implements HTMLHeadingElement {
	public HTMLHeadingElementImpl(String name) {
		super(name);
	}

	@Override
	protected void appendInnerTextImpl(StringBuffer buffer) {
		final int length = buffer.length();
		int lineBreaks;
		if (length == 0) {
			lineBreaks = 2;
		} else {
			int start = length - 4;
			if (start < 0) {
				start = 0;
			}
			lineBreaks = 0;
			for (int i = start; i < length; i++) {
				final char ch = buffer.charAt(i);
				if (ch == '\n') {
					lineBreaks++;
				}
			}
		}
		for (int i = 0; i < 2 - lineBreaks; i++) {
			buffer.append("\r\n");
		}
		super.appendInnerTextImpl(buffer);
		buffer.append("\r\n\r\n");
	}

	@Override
	protected AbstractCSS2Properties createDefaultStyleSheet() {
		final ComputedCSS2Properties css = new ComputedCSS2Properties(this);
		css.internalSetLC("font-size", getHeadingFontSizeText());
		css.internalSetLC("font-weight", "bolder");
		return css;
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		getHeadingFontSize();
		// (can't put a RenderState in the middle - messes up "em" sizes).
		// prevRenderState = new FontSizeRenderState(prevRenderState, fontSize,
		// java.awt.Font.BOLD);
		return new HeadingRenderState(prevRenderState, this);
	}

	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	private final float getHeadingFontSize() {
		final String tagName = getTagName();
		try {
			final int lastCharValue = tagName.charAt(1) - '0';
			switch (lastCharValue) {
			case 1:
				return 24.0f;
			case 2:
				return 18.0f;
			case 3:
				return 15.0f;
			case 4:
				return 12.0f;
			case 5:
				return 10.0f;
			case 6:
				return 8.0f;
			}
		} catch (final Exception thrown) {
			this.warn("getHeadingFontSize(): Bad heading tag: " + getTagName(), thrown);
		}
		return 14.0f;
	}

	private final String getHeadingFontSizeText() {
		final String tagName = getTagName();
		try {
			final int lastCharValue = tagName.charAt(1) - '0';
			switch (lastCharValue) {
			case 1:
				return "24pt";
			case 2:
				return "18pt";
			case 3:
				return "13.55pt";
			case 4:
				return "12pt";
			case 5:
				return "10pt";
			case 6:
				return "7.55pt";
			}
		} catch (final Exception thrown) {
			this.warn("getHeadingFontSizeText(): Bad heading tag: " + getTagName(), thrown);
		}
		return "14px";
	}

	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}
}
