/*
* Copyright (c) 2002 and later by MH Software-Entwicklung. All Rights Reserved.
*
* JTattoo is multiple licensed. If your are an open source developer you can use
* it under the terms and conditions of the GNU General Public License version 2.0
* or later as published by the Free Software Foundation.
*
* see: gpl-2.0.txt
*
* If you pay for a license you will become a registered user who could use the
* software under the terms and conditions of the GNU Lesser General Public License
* version 2.0 or later with classpath exception as published by the Free Software
* Foundation.
*
* see: lgpl-2.0.txt
* see: classpath-exception.txt
*
* Registered users could also use JTattoo under the terms and conditions of the
* Apache License, Version 2.0 as published by the Apache Software Foundation.
*
* see: APACHE-LICENSE-2.0.txt
*/

package com.jtattoo.plaf.acryl;

import com.mercuryred.render.interfaces.ui.Color;
import com.mercuryred.render.interfaces.ui.Font;
import com.mercuryred.render.interfaces.ui.FontMetrics;
import com.mercuryred.render.interfaces.ui.Graphics;
import com.mercuryred.render.interfaces.ui.Graphics2D;
import com.mercuryred.render.interfaces.ui.Rectangle;
import com.mercuryred.render.interfaces.ui.RenderingHints;

import com.mercuryred.render.interfaces.uiplus.JComponent;
import com.mercuryred.render.interfaces.uiplus.plaf.ColorUIResource;
import com.mercuryred.render.interfaces.uiplus.plaf.ComponentUI;
import com.mercuryred.render.interfaces.uiplus.plaf.UIResource;
import com.mercuryred.render.interfaces.uiplus.text.View;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseTabbedPaneUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * author Michael Hagen
 *
 *
 *
 */
public class AcrylTabbedPaneUI extends BaseTabbedPaneUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new AcrylTabbedPaneUI();
	}

	/** {@inheritDoc} */
	@Override
	protected Color getContentBorderColor() {
		return ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFrameColor(), 50);
	}

	/** {@inheritDoc} */
	@Override
	protected Color[] getContentBorderColors(int tabPlacement) {
		Color[] SEP_COLORS = { ColorHelper.brighter(AbstractLookAndFeel.getControlColorLight(), 20),
				AbstractLookAndFeel.getControlColorLight(),
				ColorHelper.brighter(AbstractLookAndFeel.getControlColorDark(), 20),
				AbstractLookAndFeel.getControlColorDark(),
				ColorHelper.darker(AbstractLookAndFeel.getControlColorDark(), 20) };
		return SEP_COLORS;
	}

	/** {@inheritDoc} */
	@Override
	protected Color getLoBorderColor(int tabIndex) {
		if (tabIndex == tabPane.getSelectedIndex() && tabPane.getBackgroundAt(tabIndex) instanceof ColorUIResource) {
			return ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 10);
		}
		return super.getLoBorderColor(tabIndex);
	}

	/** {@inheritDoc} */
	@Override
	protected Color[] getTabColors(int tabIndex, boolean isSelected, boolean isRollover) {
		if (tabIndex >= 0 && tabIndex < tabPane.getTabCount()) {
			boolean isEnabled = tabPane.isEnabledAt(tabIndex);
			Color backColor = tabPane.getBackgroundAt(tabIndex);
			Color[] colorArr;
			if (backColor instanceof UIResource) {
				if (isSelected) {
					colorArr = AbstractLookAndFeel.getTheme().getDefaultColors();
				} else if (isRollover && isEnabled) {
					colorArr = AbstractLookAndFeel.getTheme().getRolloverColors();
				} else {
					colorArr = AbstractLookAndFeel.getTheme().getTabColors();
				}
			} else {
				if (isSelected) {
					colorArr = ColorHelper.createColorArr(ColorHelper.brighter(backColor, 60), backColor, 20);
				} else if (isRollover && isEnabled) {
					colorArr = ColorHelper.createColorArr(ColorHelper.brighter(backColor, 80),
							ColorHelper.brighter(backColor, 20), 20);
				} else {
					colorArr = ColorHelper.createColorArr(ColorHelper.brighter(backColor, 40),
							ColorHelper.darker(backColor, 10), 20);
				}
			}
			return colorArr;
		}
		return AbstractLookAndFeel.getTheme().getTabColors();
	}

	/** {@inheritDoc} */
	@Override
	protected Font getTabFont(boolean isSelected) {
		if (isSelected) {
			return super.getTabFont(isSelected).deriveFont(Font.BOLD);
		} else {
			return super.getTabFont(isSelected);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void installDefaults() {
		super.installDefaults();
		tabAreaInsets.bottom = 5;
	}

	/** {@inheritDoc} */
	@Override
	protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title,
			Rectangle textRect, boolean isSelected) {
		Color backColor = tabPane.getBackgroundAt(tabIndex);
		if (!(backColor instanceof UIResource)) {
			super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
			return;
		}
		g.setFont(font);
		View v = getTextViewForTab(tabIndex);
		if (v != null) {
			// html
			Graphics2D g2D = (Graphics2D) g;
			Object savedRenderingHint = null;
			if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
				savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
				g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
			}
			v.paint(g, textRect);
			if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
				g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
			}
		} else {
			// plain text
			int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);

			if (tabPane.isEnabled() && tabPane.isEnabledAt(tabIndex)) {
				if (isSelected) {
					Color shadowColor = ColorHelper.darker(AbstractLookAndFeel.getWindowTitleColorDark(), 30);
					g.setColor(shadowColor);
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x - 1,
							textRect.y - 1 + metrics.getAscent());
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x - 1,
							textRect.y + 1 + metrics.getAscent());
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x + 1,
							textRect.y - 1 + metrics.getAscent());
					JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x + 1,
							textRect.y + 1 + metrics.getAscent());
					g.setColor(AbstractLookAndFeel.getTheme().getTabSelectionForegroundColor());
				} else {
					g.setColor(tabPane.getForegroundAt(tabIndex));
				}
				JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
						textRect.y + metrics.getAscent());

			} else { // tab disabled
				g.setColor(tabPane.getBackgroundAt(tabIndex).brighter());
				JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x,
						textRect.y + metrics.getAscent());
				g.setColor(tabPane.getBackgroundAt(tabIndex).darker());
				JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x - 1,
						textRect.y + metrics.getAscent() - 1);
			}
		}
	}

} // end of class AcrylTabbedPaneUI
