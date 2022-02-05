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

package com.jtattoo.plaf.aero;

import com.mercuryred.render.interfaces.ui.AlphaComposite;
import com.mercuryred.render.interfaces.ui.Color;
import com.mercuryred.render.interfaces.ui.Component;
import com.mercuryred.render.interfaces.ui.Composite;
import com.mercuryred.render.interfaces.ui.Graphics;
import com.mercuryred.render.interfaces.ui.Graphics2D;
import com.mercuryred.render.interfaces.ui.Insets;

import com.mercuryred.render.interfaces.uiplus.AbstractButton;
import com.mercuryred.render.interfaces.uiplus.ButtonModel;
import com.mercuryred.render.interfaces.uiplus.border.Border;
import com.mercuryred.render.interfaces.uiplus.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseBorders;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>AeroBorders class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AeroBorders extends BaseBorders {

	// ------------------------------------------------------------------------------------
	// Implementation of border classes
	// ------------------------------------------------------------------------------------
	public static class ButtonBorder implements Border, UIResource {

		private static final Insets insets = new Insets(4, 8, 4, 8);

		@Override
		public Insets getBorderInsets(final Component c) {
			return insets;
		}

		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
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
			Graphics2D g2D = (Graphics2D) g;
			AbstractButton button = (AbstractButton) c;
			ButtonModel model = button.getModel();
			if (model.isEnabled()) {
				g.setColor(AbstractLookAndFeel.getFrameColor());
			} else {
				g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 30));
			}
			g.drawRect(x, y, w - 2, h - 2);
			Composite composite = g2D.getComposite();
			AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g2D.setComposite(alpha);
			g.setColor(Color.white);
			g.drawLine(x + w - 1, y + 1, x + w - 1, y + h);
			g.drawLine(x + 1, y + h - 1, x + w, y + h - 1);
			g2D.setComposite(composite);
		}

	} // class ButtonBorder

	public static class InternalFrameBorder extends BaseInternalFrameBorder {

		private static final long serialVersionUID = 1L;

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
			Color borderColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
			if (isActive(c)) {
				borderColor = AbstractLookAndFeel.getWindowBorderColor();
			}
			if (!isResizable(c)) {
				Color cHi = ColorHelper.brighter(borderColor, 40);
				Color cLo = ColorHelper.darker(borderColor, 40);
				JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
				cHi = ColorHelper.darker(cHi, 20);
				cLo = ColorHelper.brighter(cLo, 20);
				JTattooUtilities.draw3DBorder(g, cHi, cLo, x + 1, y + 1, w - 2, h - 2);
				g.setColor(borderColor);
				for (int i = 2; i < DW; i++) {
					g.drawRect(i, i, w - 2 * i - 1, h - 2 * i - 1);
				}
			} else {
				int dt = w / 3;
				int db = w * 2 / 3;
				h--;
				w--;

				Color cl = ColorHelper.brighter(borderColor, 10);
				Color cr = AbstractLookAndFeel.getWindowInactiveBorderColor();
				g.setColor(cl);
				g.drawLine(x, y, x, y + h);
				g.setColor(ColorHelper.brighter(cl, 60));
				g.drawLine(x + 1, y + 1, x + 1, y + h - 1);
				g.setColor(ColorHelper.brighter(cl, 40));
				g.drawLine(x + 2, y + 2, x + 2, y + h - 2);
				g.setColor(ColorHelper.brighter(cl, 20));
				g.drawLine(x + 3, y + 3, x + 3, y + h - 3);
				g.setColor(cl);
				g.drawLine(x + 4, y + 4, x + 4, y + h - 4);

				// rechts
				g.setColor(cr);
				g.drawLine(x + w, y, x + w, y + h);
				g.setColor(ColorHelper.brighter(cr, 30));
				g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
				g.setColor(ColorHelper.brighter(cr, 60));
				g.drawLine(x + w - 2, y + 2, x + w - 2, y + h - 2);
				g.setColor(ColorHelper.brighter(cr, 90));
				g.drawLine(x + w - 3, y + 3, x + w - 3, y + h - 3);
				g.setColor(cr);
				g.drawLine(x + w - 4, y + 4, x + w - 4, y + h - 4);

				g.setColor(cl);
				g.drawLine(x + w, y, x + w, y + TRACK_WIDTH);
				g.setColor(ColorHelper.brighter(cl, 20));
				g.drawLine(x + w - 1, y + 1, x + w - 1, y + TRACK_WIDTH);
				g.setColor(ColorHelper.brighter(cl, 40));
				g.drawLine(x + w - 2, y + 2, x + w - 2, y + TRACK_WIDTH);
				g.setColor(ColorHelper.brighter(cl, 60));
				g.drawLine(x + w - 3, y + 3, x + w - 3, y + TRACK_WIDTH);
				g.setColor(cl);
				g.drawLine(x + w - 4, y + 4, x + w - 4, y + TRACK_WIDTH);

				g.setColor(cl);
				g.drawLine(x + w, y + h - TRACK_WIDTH, x + w, y + h);
				g.setColor(ColorHelper.brighter(cl, 20));
				g.drawLine(x + w - 1, y + h - TRACK_WIDTH, x + w - 1, y + h - 1);
				g.setColor(ColorHelper.brighter(cl, 40));
				g.drawLine(x + w - 2, y + h - TRACK_WIDTH, x + w - 2, y + h - 2);
				g.setColor(ColorHelper.brighter(cl, 60));
				g.drawLine(x + w - 3, y + h - TRACK_WIDTH, x + w - 3, y + h - 3);
				g.setColor(cl);
				g.drawLine(x + w - 4, y + h - TRACK_WIDTH, x + w - 4, y + h - 4);

				// oben
				g.setColor(cl);
				g.drawLine(x, y, x + dt, y);
				g.setColor(ColorHelper.brighter(cl, 60));
				g.drawLine(x + 1, y + 1, x + dt, y + 1);
				g.setColor(ColorHelper.brighter(cl, 40));
				g.drawLine(x + 2, y + 2, x + dt, y + 2);
				g.setColor(ColorHelper.brighter(cl, 20));
				g.drawLine(x + 3, y + 3, x + dt, y + 3);
				g.setColor(cl);
				g.drawLine(x + 4, y + 4, x + dt, y + 4);

				g.setColor(cr);
				g.drawLine(x + dt, y, x + w, y);
				g.setColor(ColorHelper.brighter(cr, 90));
				g.drawLine(x + dt, y + 1, x + w - 1, y + 1);
				g.setColor(ColorHelper.brighter(cr, 60));
				g.drawLine(x + dt, y + 2, x + w - 2, y + 2);
				g.setColor(ColorHelper.brighter(cr, 30));
				g.drawLine(x + dt, y + 3, x + w - 3, y + 3);
				if (isActive(c)) {
					g.setColor(ColorHelper.darker(cr, 15));
				} else {
					g.setColor(cr);
				}
				g.drawLine(x + dt, y + 4, x + w - 4, y + 4);

				g.setColor(cl);
				g.drawLine(x + w - TRACK_WIDTH, y, x + w, y);
				g.setColor(ColorHelper.brighter(cl, 60));
				g.drawLine(x + w - TRACK_WIDTH, y + 1, x + w - 1, y + 1);
				g.setColor(ColorHelper.brighter(cl, 40));
				g.drawLine(x + w - TRACK_WIDTH, y + 2, x + w - 2, y + 2);
				g.setColor(ColorHelper.brighter(cl, 20));
				g.drawLine(x + w - TRACK_WIDTH, y + 3, x + w - 3, y + 3);
				g.setColor(cl);
				g.drawLine(x + w - TRACK_WIDTH, y + 4, x + w - 4, y + 4);

				// unten
				g.setColor(cl);
				g.drawLine(x, y + h, x + db, y + h);
				g.setColor(ColorHelper.brighter(cl, 20));
				g.drawLine(x + 1, y + h - 1, x + db, y + h - 1);
				g.setColor(ColorHelper.brighter(cl, 40));
				g.drawLine(x + 2, y + h - 2, x + db, y + h - 2);
				g.setColor(ColorHelper.brighter(cl, 60));
				g.drawLine(x + 3, y + h - 3, x + db, y + h - 3);
				g.setColor(cl);
				g.drawLine(x + 4, y + h - 4, x + db, y + h - 4);

				g.setColor(cr);
				g.drawLine(x + db, y + h, x + w, y + h);
				g.setColor(ColorHelper.brighter(cr, 30));
				g.drawLine(x + db, y + h - 1, x + w - 1, y + h - 1);
				g.setColor(ColorHelper.brighter(cr, 60));
				g.drawLine(x + db, y + h - 2, x + w - 2, y + h - 2);
				g.setColor(ColorHelper.brighter(cr, 90));
				g.drawLine(x + db, y + h - 3, x + w - 3, y + h - 3);
				g.setColor(cr);
				g.drawLine(x + db, y + h - 4, x + w - 4, y + h - 4);

				g.setColor(cl);
				g.drawLine(x + w - TRACK_WIDTH, y + h, x + w, y + h);
				g.setColor(ColorHelper.brighter(cl, 20));
				g.drawLine(x + w - TRACK_WIDTH, y + h - 1, x + w - 1, y + h - 1);
				g.setColor(ColorHelper.brighter(cl, 40));
				g.drawLine(x + w - TRACK_WIDTH, y + h - 2, x + w - 2, y + h - 2);
				g.setColor(ColorHelper.brighter(cl, 60));
				g.drawLine(x + w - TRACK_WIDTH, y + h - 3, x + w - 3, y + h - 3);
				g.setColor(cl);
				g.drawLine(x + w - TRACK_WIDTH, y + h - 4, x + w - 4, y + h - 4);
			} // paintBorder
		}
	} // end of class InternalFrameBorder

	public static class RolloverToolButtonBorder implements Border, UIResource {

		private static final Insets insets = new Insets(1, 1, 1, 1);

		@Override
		public Insets getBorderInsets(final Component c) {
			return new Insets(insets.top, insets.left, insets.bottom, insets.right);
		}

		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
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
					g.setColor(Color.white);
					g.fillRect(x + 1, y + 1, w - 2, h - 2);
					g2D.setComposite(composite);
				}
			}
		}

	} // class RolloverToolButtonBorder

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

} // end of class AeroBorders
