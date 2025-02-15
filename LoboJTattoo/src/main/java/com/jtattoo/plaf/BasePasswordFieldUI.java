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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import javax.swing.text.DefaultEditorKit;

/**
 * <p>BasePasswordFieldUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BasePasswordFieldUI extends BasicPasswordFieldUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BasePasswordFieldUI();
	}

	private Border orgBorder = null;

	private FocusListener focusListener = null;

	/** {@inheritDoc} */
	@Override
	protected void installKeyboardActions() {
		super.installKeyboardActions();
		if (JTattooUtilities.isMac()) {
			InputMap im = (InputMap) UIManager.get("TextField.focusInputMap");
			int commandKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, commandKey), DefaultEditorKit.copyAction);
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, commandKey), DefaultEditorKit.pasteAction);
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, commandKey), DefaultEditorKit.cutAction);
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, InputEvent.ALT_DOWN_MASK),
					DefaultEditorKit.nextWordAction);
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, InputEvent.ALT_DOWN_MASK),
					DefaultEditorKit.previousWordAction);
		}
	}

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
						}
						getComponent().invalidate();
						getComponent().repaint();
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

} // end of clas BasePasswordFieldUI
