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

package com.jtattoo.plaf;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * <p>BaseProgressBarUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseProgressBarUI extends BasicProgressBarUI {

	// -----------------------------------------------------------------------------------------------
	protected class PropertyChangeHandler implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent e) {
			if ("selectionForeground".equals(e.getPropertyName()) && e.getNewValue() instanceof Color) {
				progressBar.invalidate();
				progressBar.repaint();
			} else if ("selectionBackground".equals(e.getPropertyName()) && e.getNewValue() instanceof Color) {
				progressBar.invalidate();
				progressBar.repaint();
			}
		}
	} // end of class PropertyChangeHandler

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseProgressBarUI();
	}

	protected PropertyChangeListener propertyChangeListener;

	/*
	 * The "selectionBackground" is the color of the text when it is painted over an
	 * unfilled area of the progress bar.
	 */
	/** {@inheritDoc} */
	@Override
	protected Color getSelectionBackground() {
		Object selectionBackground = progressBar.getClientProperty("selectionBackground");
		if (selectionBackground instanceof Color) {
			return (Color) selectionBackground;
		}
		return super.getSelectionBackground();
	}

	/*
	 * The "selectionForeground" is the color of the text when it is painted over a
	 * filled area of the progress bar.
	 */
	/** {@inheritDoc} */
	@Override
	protected Color getSelectionForeground() {
		Object selectionForeground = progressBar.getClientProperty("selectionForeground");
		if (selectionForeground instanceof Color) {
			return (Color) selectionForeground;
		}
		return super.getSelectionForeground();
	}

	/** {@inheritDoc} */
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		c.setBorder(UIManager.getBorder("ProgressBar.border"));
		propertyChangeListener = new PropertyChangeHandler();
		c.addPropertyChangeListener(propertyChangeListener);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		if (progressBar.isIndeterminate()) {
			paintIndeterminate(g, c);
		} else {
			paintDeterminate(g, c);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintDeterminate(Graphics g, JComponent c) {
		if (!(g instanceof Graphics2D)) {
			return;
		}

		Graphics2D g2D = (Graphics2D) g;
		Insets b = progressBar.getInsets(); // area for border
		int w = progressBar.getWidth() - (b.right + b.left);
		int h = progressBar.getHeight() - (b.top + b.bottom);

		// amount of progress to draw
		int amountFull = getAmountFull(b, w, h);
		Color[] colors;
		if (progressBar.getForeground() instanceof UIResource) {
			if (!JTattooUtilities.isActive(c)) {
				colors = AbstractLookAndFeel.getTheme().getInActiveColors();
			} else if (c.isEnabled()) {
				colors = AbstractLookAndFeel.getTheme().getProgressBarColors();
			} else {
				colors = AbstractLookAndFeel.getTheme().getDisabledColors();
			}
		} else {
			Color hiColor = ColorHelper.brighter(progressBar.getForeground(), 40);
			Color loColor = ColorHelper.darker(progressBar.getForeground(), 20);
			colors = ColorHelper.createColorArr(hiColor, loColor, 20);
		}
		Color cHi = ColorHelper.darker(colors[colors.length - 1], 5);
		Color cLo = ColorHelper.darker(colors[colors.length - 1], 10);
		if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
			if (JTattooUtilities.isLeftToRight(progressBar)) {
				JTattooUtilities.draw3DBorder(g, cHi, cLo, 1 + b.left, 2, amountFull - 2, h - 2);
				JTattooUtilities.fillHorGradient(g, colors, 2 + b.left, 3, amountFull - 4, h - 4);
			} else {
				JTattooUtilities.draw3DBorder(g, cHi, cLo, progressBar.getWidth() - amountFull - b.right + 2, 2,
						amountFull - 2, h - 2);
				JTattooUtilities.fillHorGradient(g, colors, progressBar.getWidth() - amountFull - b.right + 3, 3,
						amountFull - 4, h - 4);
			}
		} else { // VERTICAL
			JTattooUtilities.draw3DBorder(g, cHi, cLo, 2, h - amountFull + 2, w - 2, amountFull - 2);
			JTattooUtilities.fillVerGradient(g, colors, 3, h - amountFull + 3, w - 4, amountFull - 4);
		}

		// Deal with possible text painting
		if (progressBar.isStringPainted()) {
			Object savedRenderingHint = null;
			if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
				savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
				g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
			}
			paintString(g, b.left, b.top, w, h, amountFull, b);
			if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
				g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintIndeterminate(Graphics g, JComponent c) {
		if (!(g instanceof Graphics2D)) {
			return;
		}
		Graphics2D g2D = (Graphics2D) g;

		Insets b = progressBar.getInsets(); // area for border
		int barRectWidth = progressBar.getWidth() - (b.right + b.left);
		int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

		Color[] colors;
		if (progressBar.getForeground() instanceof UIResource) {
			if (!JTattooUtilities.isActive(c)) {
				colors = AbstractLookAndFeel.getTheme().getInActiveColors();
			} else if (c.isEnabled()) {
				colors = AbstractLookAndFeel.getTheme().getProgressBarColors();
			} else {
				colors = AbstractLookAndFeel.getTheme().getDisabledColors();
			}
		} else {
			Color hiColor = ColorHelper.brighter(progressBar.getForeground(), 40);
			Color loColor = ColorHelper.darker(progressBar.getForeground(), 20);
			colors = ColorHelper.createColorArr(hiColor, loColor, 20);
		}

		Color cHi = ColorHelper.darker(colors[colors.length - 1], 5);
		Color cLo = ColorHelper.darker(colors[colors.length - 1], 10);

		// Paint the bouncing box.
		Rectangle box = getBox(null);
		if (box != null) {
			g2D.setColor(progressBar.getForeground());
			JTattooUtilities.draw3DBorder(g, cHi, cLo, box.x + 1, box.y + 1, box.width - 2, box.height - 2);
			if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
				JTattooUtilities.fillHorGradient(g, colors, box.x + 2, box.y + 2, box.width - 4, box.height - 4);
			} else {
				JTattooUtilities.fillVerGradient(g, colors, box.x + 2, box.y + 2, box.width - 4, box.height - 4);
			}

			// Deal with possible text painting
			if (progressBar.isStringPainted()) {
				Object savedRenderingHint = null;
				if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
					savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
					g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
							AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
				}
				if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
					paintString(g2D, b.left, b.top, barRectWidth, barRectHeight, box.width, b);
				} else {
					paintString(g2D, b.left, b.top, barRectWidth, barRectHeight, box.height, b);
				}
				if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
					g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
				}
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintString(Graphics g, int x, int y, int width, int height, int amountFull, Insets b) {
		boolean indeterminate = progressBar.isIndeterminate();
		if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
			if (JTattooUtilities.isLeftToRight(progressBar)) {
				if (indeterminate) {
					boxRect = getBox(boxRect);
					paintString(g, x, y, width, height, boxRect.x, boxRect.width, b);
				} else {
					paintString(g, x, y, width, height, x, amountFull, b);
				}
			} else {
				paintString(g, x, y, width, height, x + width - amountFull, amountFull, b);
			}
		} else {
			if (indeterminate) {
				boxRect = getBox(boxRect);
				paintString(g, x, y, width, height, boxRect.y, boxRect.height, b);
			} else {
				paintString(g, x, y, width, height, y + height - amountFull, amountFull, b);
			}
		}
	}

	private void paintString(Graphics g, int x, int y, int width, int height, int fillStart, int amountFull, Insets b) {
		if (!(g instanceof Graphics2D)) {
			return;
		}

		Graphics2D g2D = (Graphics2D) g;
		String progressString = progressBar.getString();
		g2D.setFont(progressBar.getFont());
		Point renderLocation = getStringPlacement(g2D, progressString, x, y, width, height);
		Rectangle savedClip = g2D.getClipBounds();

		if (progressBar.getOrientation() == SwingConstants.HORIZONTAL) {
			g2D.setColor(getSelectionBackground());
			JTattooUtilities.drawString(progressBar, g2D, progressString, renderLocation.x, renderLocation.y);
			g2D.setColor(getSelectionForeground());
			g2D.clipRect(fillStart, y, amountFull, height);
			JTattooUtilities.drawString(progressBar, g2D, progressString, renderLocation.x, renderLocation.y);
		} else { // VERTICAL
			g2D.setColor(getSelectionBackground());
			AffineTransform rotate = AffineTransform.getRotateInstance(Math.PI / 2);
			g2D.setFont(progressBar.getFont().deriveFont(rotate));
			renderLocation = getStringPlacement(g2D, progressString, x, y, width, height);
			JTattooUtilities.drawString(progressBar, g2D, progressString, renderLocation.x, renderLocation.y);
			g2D.setColor(getSelectionForeground());
			g2D.clipRect(x, fillStart, width, amountFull);
			JTattooUtilities.drawString(progressBar, g2D, progressString, renderLocation.x, renderLocation.y);
		}
		g2D.setClip(savedClip);
	}

	/** {@inheritDoc} */
	@Override
	public void uninstallUI(JComponent c) {
		c.removePropertyChangeListener(propertyChangeListener);
		super.uninstallUI(c);
	}

} // end of class BaseProgressBarUI
