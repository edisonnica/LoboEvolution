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

package org.loboevolution.html.style;

import com.gargoylesoftware.css.dom.CSSValueImpl;
import com.gargoylesoftware.css.dom.CSSValueImpl.CSSPrimitiveValueType;
import com.loboevolution.store.laf.LAFSettings;
import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.ListValues;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.net.HttpNetwork;

import java.awt.*;
import java.util.ArrayList;

/**
 * <p>HtmlValues class.</p>
 */
public class HtmlValues {

	/**
	 * <p>getListStyle.</p>
	 *
	 * @param listStyleText a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.style.ListStyle} object.
	 * @param baseUri a {@link java.lang.String} object.
	 */
	public static ListStyle getListStyle(String listStyleText, String baseUri) {
		final ListStyle listStyle = new ListStyle();
		final String[] tokens = HtmlValues.splitCssValue(listStyleText);
		for (final String token : tokens) {
			final ListValues listStyleType = HtmlValues.getListStyleType(token);
			if (listStyleType != ListValues.TYPE_UNSET && listStyleType != ListValues.NONE && listStyle.getType() < 1) {
				listStyle.setType(listStyleType.getValue());
			} else if (HtmlValues.isUrl(token)) {
				listStyle.setType(ListValues.TYPE_URL.getValue());
				listStyle.setImage(getListStyleImage(token, baseUri));
			} else {
				final ListValues listStylePosition = HtmlValues.getListStylePosition(token);
				if (listStylePosition != ListValues.POSITION_UNSET && listStylePosition != ListValues.NONE) {
					listStyle.setPosition(listStylePosition.getValue());
				}
			}
		}
		return listStyle;
	}
	
	
	/**
	 * <p>getListStyleImage.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a {@link java.awt.Image} object.
	 * @param baseUri a {@link java.lang.String} object.
	 */
	public static Image getListStyleImage(String token, String baseUri) {
		Image image;
		String start = "url(";
		int startIdx = start.length();
		int closingIdx = token.lastIndexOf(')');
		String quotedUri = token.substring(startIdx, closingIdx);
		String[] items = { "http", "https", "file" };
		if (Strings.containsWords(quotedUri, items)) {
			try {
				image = HttpNetwork.getImage(quotedUri, null);
			} catch (Exception e) {
				image = null;
			}
		} else {
			try {
				image = HttpNetwork.getImage(quotedUri, baseUri);
			} catch (Exception e) {
				image = null;
			}
		}

		return image;
	}

	/**
	 * <p>getListStylePosition.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.ListValues} object.
	 */
	public static ListValues getListStylePosition(String token) {
		final String tokenTL = token.toLowerCase();
		CSSValues tkn = CSSValues.get(tokenTL);
		switch (tkn) {
		case INSIDE:
			return ListValues.POSITION_INSIDE;
		case OUTSIDE:
			return ListValues.POSITION_OUTSIDE;
		default:
			return ListValues.POSITION_UNSET;
		}
	}

	/**
	 * <p>getListStyleType.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.ListValues} object.
	 */
	public static ListValues getListStyleType(String token) {
		final String tokenTL = token.toLowerCase();
		CSSValues tkn = CSSValues.get(tokenTL);
		switch (tkn) {
		case NONE:
			return ListValues.TYPE_NONE;
		case DISC:
			return ListValues.TYPE_DISC;
		case CIRCLE:
			return ListValues.TYPE_CIRCLE;
		case SQUARE:
			return ListValues.TYPE_SQUARE;
		case DECIMAL:
			return ListValues.TYPE_DECIMAL;
		case DECIMAL_LEADING_ZERO:
			return ListValues.TYPE_DECIMAL_LEADING_ZERO;
		case LOWER_ALPHA:
		case LOWER_LATIN:
			return ListValues.TYPE_LOWER_ALPHA;
		case UPPER_ALPHA:
		case UPPER_LATIN:
			return ListValues.TYPE_UPPER_ALPHA;
		case LOWER_ROMAN:
			return ListValues.TYPE_LOWER_ROMAN;
		case UPPER_ROMAN:
			return ListValues.TYPE_UPPER_ROMAN;
		default:
			return ListValues.TYPE_UNSET;
		}
	}

	/**
	 * <p>getPixelSize.</p>
	 *
	 * @param spec a {@link java.lang.String} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param errorValue a int.
	 * @return a int.
	 */
	public static int getPixelSize(String spec, RenderState renderState, Window window, int errorValue) {
		try {
			final int dpi = GraphicsEnvironment.isHeadless() ? 72 : Toolkit.getDefaultToolkit().getScreenResolution();
			final String lcSpec = spec.toLowerCase();
			String units = "";
			String text = "";
			if (isUnits(spec)) {
				if (spec.endsWith("q")) {
					units = lcSpec.substring(lcSpec.length() - 1);
					text = lcSpec.substring(0, lcSpec.length() - 1);
				} else if (spec.endsWith("rem")) {
					units = lcSpec.substring(lcSpec.length() - 3);
					text = lcSpec.substring(0, lcSpec.length() - 3);
				} else {
					units = lcSpec.substring(lcSpec.length() - 2);
					text = lcSpec.substring(0, lcSpec.length() - 2);
				}
			}

			switch (units) {
			case "px":
                final double val = Double.parseDouble(text);
                final double inches = val / 96;
                return (int) Math.round(dpi * inches);
			case "em":
				final FontFactory FONT_FACTORY = FontFactory.getInstance();
				final Font DEFAULT_FONT = FONT_FACTORY.getFont(FontValues.getDefaultFontKey());
				final Font f = (renderState == null) ? DEFAULT_FONT : renderState.getFont();
				final int fontSize = f.getSize();
				final double pixelSize = fontSize * dpi / 96;
				return (int) Math.round(pixelSize * Double.parseDouble(text));
			case "rem":
					final float fs = new LAFSettings().getInstance().getFontSize();
					return (int) Math.round(fs * Double.parseDouble(text));
			case "pt":
				return inches(72, dpi, text);
			case "pc":
				return inches(6, dpi, text);
			case "cm":
				return inches(2.54, dpi, text);
			case "mm":
				return inches(25.4, dpi, text);
			case "q":
				return inches(1016, dpi, text);
			case "ex":
				final double xHeight = renderState.getFontMetrics().getAscent() * 0.47;
				return (int) Math.round(xHeight * Double.parseDouble(text));
			case "in":
				return (int) Math.round(dpi * Double.parseDouble(text));
			case "vh":
				if (window != null) {
					final double ih = window.getInnerHeight();
					return (int) Math.round(ih * Double.parseDouble(text) / 100.0);
				} else {
					return (int) Math.round(Double.parseDouble(lcSpec));
				}
			case "vw":
				if (window != null) {
					final double iw = window.getInnerWidth();
					return (int) Math.round(iw * Double.parseDouble(text) / 100.0);
				} else {
					return (int) Math.round(Double.parseDouble(lcSpec));
				}
			default:
				return (int) Math.round(Double.parseDouble(lcSpec));
			}
		} catch (final Exception ex) {
			return errorValue;
		}
	}
	
	/**
	 * <p>getPixelSize.</p>
	 *
	 * @param spec a {@link java.lang.String} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param errorValue a int.
	 * @param availSize a int.
	 * @return a int.
	 */
	public static int getPixelSize(String spec, RenderState renderState, Window window, int errorValue, int availSize) {
		try {
			if (spec.endsWith("%")) {
				final String perText = spec.substring(0, spec.length() - 1);
				final double val = Double.parseDouble(perText);
				return (int) Math.round(availSize * val / 100.0);
			} else {
				return getPixelSize(spec, renderState, window, errorValue);
			}
		} catch (final Exception nfe) {
			return errorValue;
		}
	}
	
	/**
	 * <p>resolutionValue.</p>
	 *
	 * @param cssValue a {@link com.gargoylesoftware.css.dom.CSSValueImpl} object.
	 * @return a int.
	 */
	public static int resolutionValue(final CSSValueImpl cssValue) {
        if (cssValue == null) {
            return -1;
        }
        
        if (cssValue.getPrimitiveType() == CSSPrimitiveValueType.CSS_DIMENSION) {
        	String units = cssValue.getCssText().substring(cssValue.getCssText().length() - 2);
        	switch (units) {
        	case "dpi":
        		return (int)cssValue.getDoubleValue();
        	case "dpcm":
        		return (int)( 2.54f * cssValue.getDoubleValue());
        	case "dppx":
        		return (int)( 96 * cssValue.getDoubleValue());
        	default:
        		return (int)cssValue.getDoubleValue();
        	}
       }
        return -1;
    }

	/**
	 * <p>isBackgroundPosition.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isBackgroundPosition(String token) {
		return isLength(token) || token.endsWith("%") || token.equalsIgnoreCase("top")
				|| token.equalsIgnoreCase("center") || token.equalsIgnoreCase("bottom")
				|| token.equalsIgnoreCase("left") || token.equalsIgnoreCase("right");
	}

	/**
	 * <p>isBackgroundRepeat.</p>
	 *
	 * @param repeat a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isBackgroundRepeat(String repeat) {
		final String repeatTL = repeat.toLowerCase();
		return repeatTL.indexOf("repeat") != -1;
	}

	/**
	 * <p>isBorderStyle.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isBorderStyle(String token) {
		final String tokenTL = token.toLowerCase();
		return tokenTL.equals("solid") || tokenTL.equals("dashed") || tokenTL.equals("dotted")
				|| tokenTL.equals("double") || tokenTL.equals("none") || tokenTL.equals("hidden")
				|| tokenTL.equals("groove") || tokenTL.equals("ridge") || tokenTL.equals("inset")
				|| tokenTL.equals("outset");
	}

	/**
	 * <p>isUrl.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isUrl(String token) {
		return token.toLowerCase().startsWith("url(");
	}
	
	/**
	 * <p>isGradient.</p>
	 *
	 * @param token a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean isGradient(String token) {
		return token.toLowerCase().contains("gradient");
	}
	
	/**
	 * <p>quoteAndEscape.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String quoteAndEscape(String text) {
		final StringBuilder result = new StringBuilder();
		result.append("'");
		int index = 0;
		final int length = text.length();
		while (index < length) {
			final char ch = text.charAt(index);
			switch (ch) {
			case '\'':
				result.append("\\'");
				break;
			case '\\':
				result.append("\\\\");
				break;
			default:
				result.append(ch);
			}
			index++;
		}
		result.append("'");
		return result.toString();
	}

	/**
	 * <p>splitCssValue.</p>
	 *
	 * @param cssValue a {@link java.lang.String} object.
	 * @return an array of {@link java.lang.String} objects.
	 */
	public static String[] splitCssValue(String cssValue) {
		final ArrayList<String> tokens = new ArrayList<>(4);
		final int len = cssValue.length();
		int parenCount = 0;
		StringBuilder currentWord = null;
		for (int i = 0; i < len; i++) {
			final char ch = cssValue.charAt(i);
			switch (ch) {
			case '(':
				parenCount++;
				if (currentWord == null) {
					currentWord = new StringBuilder();
				}
				currentWord.append(ch);
				break;
			case ')':
				parenCount--;
				if (currentWord == null) {
					currentWord = new StringBuilder();
				}
				currentWord.append(ch);
				break;
			case ' ':
			case '\t':
			case '\n':
			case '\r':
				if (parenCount == 0) {
					tokens.add(currentWord.toString());
					currentWord = null;
					break;
				} else {
					// Fall through - no break
				}
			default:
				if (currentWord == null) {
					currentWord = new StringBuilder();
				}
				currentWord.append(ch);
				break;
			}
		}
		if (currentWord != null) {
			tokens.add(currentWord.toString());
		}
		return tokens.toArray(new String[0]);
	}

	/**
	 * <p>unquoteAndUnescape.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String unquoteAndUnescape(String text) {
		final StringBuilder result = new StringBuilder();
		int index = 0;
		final int length = text.length();
		boolean escape = false;
		boolean single = false;
		if (index < length) {
			final char ch = text.charAt(index);
			switch (ch) {
			case '\'':
				single = true;
				break;
			case '"':
				break;
			case '\\':
				escape = true;
				break;
			default:
				result.append(ch);
			}
			index++;
		}
		OUTER: for (; index < length; index++) {
			final char ch = text.charAt(index);
			switch (ch) {
			case '\'':
				if (escape || !single) {
					escape = false;
					result.append(ch);
				} else {
					break OUTER;
				}
				break;
			case '"':
				if (escape || single) {
					escape = false;
					result.append(ch);
				} else {
					break OUTER;
				}
				break;
			case '\\':
				if (escape) {
					escape = false;
					result.append(ch);
				} else {
					escape = true;
				}
				break;
			default:
				if (escape) {
					escape = false;
					result.append('\\');
				}
				result.append(ch);
			}
		}
		return result.toString();
	}

	public static boolean isUnits(String token) {
		return token.endsWith("px") ||
				token.endsWith("pt") ||
				token.endsWith("pc") ||
				token.endsWith("cm") ||
				token.endsWith("mm") ||
				token.endsWith("ex") ||
				token.endsWith("em") ||
				token.endsWith("in") ||
				token.endsWith("q") ||
				token.endsWith("vh") ||
				token.endsWith("vw") ||
				token.endsWith("rem");
	}

	private static int inches(double value, int dpi, String text) {
		double val = Double.parseDouble(text);
		final double inches = val / value;
		return (int) Math.round(dpi * inches);
	}

	private static boolean isLength(String token) {
		if (isUnits(token)) {
			return true;
		} else {
			try {
				Double.parseDouble(token);
				return true;
			} catch (final Exception nfe) {
				return false;
			}
		}
	}
}
