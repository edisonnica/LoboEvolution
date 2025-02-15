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

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTreeUI;

/**
 * author Michael Hagen
 *
 *
 *
 */
public class BaseTreeUI extends BasicTreeUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseTreeUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintHorizontalLine(Graphics g, JComponent c, int y, int left, int right) {
		drawDashedHorizontalLine(g, y, left, right);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintVerticalLine(Graphics g, JComponent c, int x, int top, int bottom) {
		drawDashedVerticalLine(g, x, top, bottom);
	}

} // end of class BaseTreeUI
