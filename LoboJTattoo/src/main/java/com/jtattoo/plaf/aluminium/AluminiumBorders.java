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
package com.jtattoo.plaf.aluminium;

import com.mercuryred.render.interfaces.ui.AlphaComposite;
import com.mercuryred.render.interfaces.ui.Color;
import com.mercuryred.render.interfaces.ui.Component;
import com.mercuryred.render.interfaces.ui.Composite;
import com.mercuryred.render.interfaces.ui.GradientPaint;
import com.mercuryred.render.interfaces.ui.Graphics;
import com.mercuryred.render.interfaces.ui.Graphics2D;
import com.mercuryred.render.interfaces.ui.Insets;

import com.mercuryred.render.interfaces.uiplus.AbstractButton;
import com.mercuryred.render.interfaces.uiplus.ButtonModel;
import com.mercuryred.render.interfaces.uiplus.border.Border;
import com.mercuryred.render.interfaces.uiplus.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseBorders;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>AluminiumBorders class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AluminiumBorders extends BaseBorders {

	// ------------------------------------------------------------------------------------
	// Implementation of border classes
	// ------------------------------------------------------------------------------------
	public static class ButtonBorder implements Border, UIResource {

		@Override
		public Insets getBorderInsets(final Component c) {
			if (AbstractLookAndFeel.getTheme().doDrawSquareButtons()) {
				return new Insets(3, 4, 3, 4);
			} else {
				return new Insets(2, 12, 2, 12);
			}
		}

		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			Insets insets = getBorderInsets(c);
			borderInsets.left = insets.left;
			borderInsets.top = insets.top;
			borderInsets.right = insets.right;
			borderInsets.bottom = insets.bottom;
			return borderInsets;
		}

		@Override
		public boolean isBorderOpaque() {
			return true;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
		}

	} // end of class ButtonBorder

	public static class InternalFrameBorder extends BaseInternalFrameBorder {

		private static final long serialVersionUID = 1L;

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
			Graphics2D g2D = (Graphics2D) g;
			Color titleColor = AbstractLookAndFeel.getWindowInactiveTitleBackgroundColor();
			if (isActive(c)) {
				titleColor = AbstractLookAndFeel.getWindowTitleBackgroundColor();
			}
			int th = getTitleHeight(c);

			g.setColor(titleColor);
			g.fillRect(1, 1, w, DW);
			g.fillRect(1, h - DW, w, DW - 1);

			if (isActive(c)) {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 1, DW, DW,
						th + 1);
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), w - DW, DW,
						DW, th + 1);
				Color c1 = AbstractLookAndFeel.getTheme().getWindowTitleColorDark();
				Color c2 = AbstractLookAndFeel.getTheme().getWindowTitleColorLight();
				g2D.setPaint(new GradientPaint(0, DW + th + 1, c1, 0, h - th - 2 * DW, c2));
				g.fillRect(1, DW + th + 1, DW - 1, h - th - 2 * DW);
				g.fillRect(w - DW, DW + th + 1, DW - 1, h - th - 2 * DW);
				g2D.setPaint(null);
			} else {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 1,
						DW, DW, th + 1);
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(),
						w - DW, DW, DW, th + 1);
				Color c1 = AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorDark();
				Color c2 = AbstractLookAndFeel.getTheme().getWindowInactiveTitleColorLight();
				g2D.setPaint(new GradientPaint(0, DW + th + 1, c1, 0, h - th - 2 * DW, c2));
				g.fillRect(1, DW + th + 1, DW - 1, h - th - 2 * DW);
				g.fillRect(w - DW, DW + th + 1, DW - 1, h - th - 2 * DW);
				g2D.setPaint(null);
			}

			Color borderColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
			if (isActive(c)) {
				borderColor = AbstractLookAndFeel.getWindowBorderColor();
			}
			g.setColor(borderColor);
			g.drawRect(0, 0, w - 1, h - 1);
			g.drawLine(x + DW - 1, y + INSETS.top + th, x + DW - 1, y + h - DW);
			g.drawLine(x + w - DW, y + INSETS.top + th, x + w - DW, y + h - DW);
			g.drawLine(x + DW - 1, y + h - DW, x + w - DW, y + h - DW);

			g.setColor(new Color(220, 220, 220));
			g.drawLine(1, 1, w - 3, 1);
			g.drawLine(1, 1, 1, h - 2);
		}

	} // end of class InternalFrameBorder

	public static class RolloverToolButtonBorder implements Border, UIResource {

		private static final Insets INSETS = new Insets(1, 1, 1, 1);

		@Override
		public Insets getBorderInsets(final Component c) {
			return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
		}

		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			borderInsets.left = INSETS.left;
			borderInsets.top = INSETS.top;
			borderInsets.right = INSETS.right;
			borderInsets.bottom = INSETS.bottom;
			return borderInsets;
		}

		@Override
		public boolean isBorderOpaque() {
			return true;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
			AbstractButton button = (AbstractButton) c;
			ButtonModel model = button.getModel();
			Color loColor = AbstractLookAndFeel.getFrameColor();
			if (model.isEnabled()) {
				if (model.isPressed() && model.isArmed() || model.isSelected()) {
					Graphics2D g2D = (Graphics2D) g;
					Composite composite = g2D.getComposite();
					g.setColor(loColor);
					g.drawRect(x, y, w - 1, h - 1);
					AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f);
					g2D.setComposite(alpha);
					g.setColor(Color.black);
					g.fillRect(x + 1, y + 1, w - 2, h - 2);
					g2D.setComposite(composite);
				} else if (model.isRollover()) {
					Graphics2D g2D = (Graphics2D) g;
					Composite composite = g2D.getComposite();
					g.setColor(loColor);
					g.drawRect(x, y, w - 1, h - 1);
					AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
					g2D.setComposite(alpha);
					g.setColor(AbstractLookAndFeel.getTheme().getSelectionBackgroundColor());
					g.fillRect(x + 1, y + 1, w - 2, h - 2);
					g2D.setComposite(composite);
				}
			}
		}
	} // end of class RolloverToolButtonBorder

	// ------------------------------------------------------------------------------------
	// Lazy access methods
	// ------------------------------------------------------------------------------------
	/**
	 * <p>getButtonBorder.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.border.Border} object.
	 */
	public static Border getButtonBorder() {
		if (buttonBorder == null) {
			buttonBorder = new ButtonBorder();
		}
		return buttonBorder;
	}

	/**
	 * <p>getInternalFrameBorder.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.border.Border} object.
	 */
	public static Border getInternalFrameBorder() {
		if (internalFrameBorder == null) {
			internalFrameBorder = new InternalFrameBorder();
		}
		return internalFrameBorder;
	}

	/**
	 * <p>getRolloverToolButtonBorder.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.border.Border} object.
	 */
	public static Border getRolloverToolButtonBorder() {
		if (rolloverToolButtonBorder == null) {
			rolloverToolButtonBorder = new RolloverToolButtonBorder();
		}
		return rolloverToolButtonBorder;
	}

	/**
	 * <p>getToggleButtonBorder.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.border.Border} object.
	 */
	public static Border getToggleButtonBorder() {
		return getButtonBorder();
	}

} // end of class AluminiumBorders
