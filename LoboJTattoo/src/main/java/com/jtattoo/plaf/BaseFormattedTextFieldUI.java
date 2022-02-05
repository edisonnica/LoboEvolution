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

import com.mercuryred.render.interfaces.ui.Graphics;
import com.mercuryred.render.interfaces.ui.Graphics2D;
import com.mercuryred.render.interfaces.ui.RenderingHints;
import com.mercuryred.render.interfaces.ui.event.FocusEvent;
import com.mercuryred.render.interfaces.ui.event.FocusListener;

import com.mercuryred.render.interfaces.uiplus.JComponent;
import com.mercuryred.render.interfaces.uiplus.LookAndFeel;
import com.mercuryred.render.interfaces.uiplus.UIManager;
import com.mercuryred.render.interfaces.uiplus.border.Border;
import com.mercuryred.render.interfaces.uiplus.plaf.ComponentUI;
import com.mercuryred.render.interfaces.uiplus.plaf.UIResource;
import com.mercuryred.render.interfaces.uiplus.plaf.basic.BasicFormattedTextFieldUI;

/**
 * <p>BaseFormattedTextFieldUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseFormattedTextFieldUI extends BasicFormattedTextFieldUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseFormattedTextFieldUI();
	}

	private Border orgBorder = null;

	private FocusListener focusListener = null;

	/** {@inheritDoc} */
	@Override
	protected void installListeners() {
		super.installListeners();

		if (AbstractLookAndFeel.getTheme().doShowFocusFrame()) {
			focusListener = new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {
					if (getComponent() != null) {
						orgBorder = getComponent().getBorder();
						LookAndFeel laf = UIManager.getLookAndFeel();
						if (laf instanceof AbstractLookAndFeel && orgBorder instanceof UIResource) {
							Border focusBorder = ((AbstractLookAndFeel) laf).getBorderFactory().getFocusFrameBorder();
							getComponent().setBorder(focusBorder);
						}
						getComponent().invalidate();
						getComponent().repaint();
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (getComponent() != null) {
						if (orgBorder instanceof UIResource) {
							getComponent().setBorder(orgBorder);
							getComponent().invalidate();
							getComponent().repaint();
						}
					}
				}
			};
			getComponent().addFocusListener(focusListener);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(Graphics g) {
		g.setColor(getComponent().getBackground());
		if (AbstractLookAndFeel.getTheme().doShowFocusFrame()) {
			if (getComponent().hasFocus() && getComponent().isEditable()) {
				g.setColor(AbstractLookAndFeel.getTheme().getFocusBackgroundColor());
			}
		}
		g.fillRect(0, 0, getComponent().getWidth(), getComponent().getHeight());
	}

	/** {@inheritDoc} */
	@Override
	protected void paintSafely(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		Object savedRenderingHint = null;
		if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
			savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
			g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
		}
		super.paintSafely(g);
		if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
			g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void uninstallListeners() {
		getComponent().removeFocusListener(focusListener);
		focusListener = null;
		super.uninstallListeners();
	}

} // end of class BaseFormattedTextFieldUI
