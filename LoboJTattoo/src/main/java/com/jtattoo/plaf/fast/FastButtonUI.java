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
package com.jtattoo.plaf.fast;

import com.mercuryred.render.interfaces.ui.Color;
import com.mercuryred.render.interfaces.ui.Graphics;

import com.mercuryred.render.interfaces.uiplus.AbstractButton;
import com.mercuryred.render.interfaces.uiplus.ButtonModel;
import com.mercuryred.render.interfaces.uiplus.JComponent;
import com.mercuryred.render.interfaces.uiplus.JMenuBar;
import com.mercuryred.render.interfaces.uiplus.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseButtonUI;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>FastButtonUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class FastButtonUI extends BaseButtonUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new FastButtonUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(Graphics g, AbstractButton b) {
		if (b.isContentAreaFilled() && !(b.getParent() instanceof JMenuBar)) {
			Color backColor = b.getBackground();
			ButtonModel model = b.getModel();
			if (model.isEnabled()) {
				if (model.isPressed() && model.isArmed()) {
					backColor = ColorHelper.darker(backColor, 30);
				}
			} else {
				backColor = ColorHelper.brighter(AbstractLookAndFeel.getDisabledForegroundColor(), 80);
			}
			g.setColor(backColor);
			g.fillRect(0, 0, b.getWidth(), b.getHeight());
		}
	}

} // end of FastButtonUI
