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

import com.mercuryred.render.interfaces.uiplus.JRootPane;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseRootPaneUI;
import com.jtattoo.plaf.BaseTitlePane;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>FastTitlePane class.</p>
 *
 * Author Michael Hagen
 *
 */
public class FastTitlePane extends BaseTitlePane {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for FastTitlePane.</p>
	 *
	 * @param root a {@link com.mercuryred.render.interfaces.uiplus.JRootPane} object.
	 * @param ui a {@link com.jtattoo.plaf.BaseRootPaneUI} object.
	 */
	public FastTitlePane(JRootPane root, BaseRootPaneUI ui) {
		super(root, ui);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBackground(Graphics g) {
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getWindowTitleBackgroundColor());
			g.fillRect(0, 0, getWidth(), getHeight());
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleBackgroundColor());
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(Graphics g) {
		Color borderColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
		if (isActive()) {
			borderColor = AbstractLookAndFeel.getWindowBorderColor();
		}
		JTattooUtilities.draw3DBorder(g, ColorHelper.brighter(borderColor, 30), ColorHelper.darker(borderColor, 5), 0,
				0, getWidth(), getHeight());
	}

} // end of class FastTitlePane
