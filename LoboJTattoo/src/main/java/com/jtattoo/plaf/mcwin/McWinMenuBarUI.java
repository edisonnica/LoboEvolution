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
package com.jtattoo.plaf.mcwin;

import com.mercuryred.render.interfaces.ui.Color;
import com.mercuryred.render.interfaces.ui.Graphics;

import com.mercuryred.render.interfaces.uiplus.JComponent;
import com.mercuryred.render.interfaces.uiplus.JMenuBar;
import com.mercuryred.render.interfaces.uiplus.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseBorders;
import com.jtattoo.plaf.BaseMenuBarUI;
import com.jtattoo.plaf.ColorHelper;

/**
 * <p>McWinMenuBarUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class McWinMenuBarUI extends BaseMenuBarUI {

	private static final Color[] SHADOW_COLORS = ColorHelper.createColorArr(Color.white, new Color(240, 240, 240), 8);

	/** {@inheritDoc} */
	public static ComponentUI createUI(JComponent x) {
		return new McWinMenuBarUI();
	}

	/** {@inheritDoc} */
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		if (c != null && c instanceof JMenuBar) {
			c.setBorder(BaseBorders.getMenuBarBorder());
			((JMenuBar) c).setBorderPainted(true);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		if (AbstractLookAndFeel.getTheme().isBackgroundPatternOn()) {
			McWinUtils.fillComponent(g, c, SHADOW_COLORS);
		} else {
			super.paint(g, c);
		}
	}

} // end of class
