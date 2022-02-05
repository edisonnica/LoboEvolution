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

package com.jtattoo.plaf.mint;

import com.mercuryred.render.interfaces.ui.Dimension;
import com.mercuryred.render.interfaces.ui.Graphics;

import com.mercuryred.render.interfaces.uiplus.BorderFactory;
import com.mercuryred.render.interfaces.uiplus.Icon;
import com.mercuryred.render.interfaces.uiplus.JButton;
import com.mercuryred.render.interfaces.uiplus.JComponent;
import com.mercuryred.render.interfaces.uiplus.border.Border;
import com.mercuryred.render.interfaces.uiplus.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseComboBoxUI;
import com.jtattoo.plaf.BaseIcons;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.NoFocusButton;

/**
 * <p>MintComboBoxUI class.</p>
 *
 *
 *
 */
public class MintComboBoxUI extends BaseComboBoxUI {

	// ------------------------------------------------------------------------------------
	static class ArrowButton extends NoFocusButton {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(final Graphics g) {
			Dimension size = getSize();
			if (isEnabled()) {
				if (getModel().isArmed() && getModel().isPressed()) {
					JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getPressedColors(), 0, 0,
							size.width, size.height);
				} else if (getModel().isRollover()) {
					JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), 0, 0,
							size.width, size.height);
				} else if (JTattooUtilities.isActive(this)) {
					JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getDefaultColors(), 0, 0,
							size.width, size.height);
				} else {
					JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getInActiveColors(), 0, 0,
							size.width, size.height);
				}
			} else {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getDisabledColors(), 0, 0,
						size.width, size.height);
			}
			Icon icon = BaseIcons.getComboBoxIcon();
			int x = (size.width - icon.getIconWidth()) / 2;
			int y = (size.height - icon.getIconHeight()) / 2;
			if (getModel().isPressed() && getModel().isArmed()) {
				icon.paintIcon(this, g, x + 2, y + 1);
			} else {
				icon.paintIcon(this, g, x + 1, y);
			}
			paintBorder(g);
		}

	} // end of end class ArrowButton

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new MintComboBoxUI();
	}

	/** {@inheritDoc} */
	@Override
	public JButton createArrowButton() {
		ArrowButton button = new ArrowButton();
		if (JTattooUtilities.isLeftToRight(comboBox)) {
			Border border = BorderFactory.createMatteBorder(0, 1, 0, 0, AbstractLookAndFeel.getFrameColor());
			button.setBorder(border);
		} else {
			Border border = BorderFactory.createMatteBorder(0, 0, 0, 1, AbstractLookAndFeel.getFrameColor());
			button.setBorder(border);
		}
		return button;
	}

} // end of class MintComboBox
