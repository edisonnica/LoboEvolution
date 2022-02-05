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
package com.jtattoo.plaf.graphite;

import com.mercuryred.render.interfaces.ui.Graphics;

import com.mercuryred.render.interfaces.uiplus.ButtonModel;
import com.mercuryred.render.interfaces.uiplus.JComponent;
import com.mercuryred.render.interfaces.uiplus.JMenu;
import com.mercuryred.render.interfaces.uiplus.JMenuItem;
import com.mercuryred.render.interfaces.uiplus.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseRadioButtonMenuItemUI;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>GraphiteRadioButtonMenuItemUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class GraphiteRadioButtonMenuItemUI extends BaseRadioButtonMenuItemUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new GraphiteRadioButtonMenuItemUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(Graphics g, JComponent c, int x, int y, int w, int h) {
		JMenuItem mi = (JMenuItem) c;
		ButtonModel model = mi.getModel();
		if (model.isArmed() || c instanceof JMenu && model.isSelected()) {
			JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getMenuSelectionColors(), x, y, w, h);
		} else {
			super.paintBackground(g, c, x, y, w, h);
		}
	}

} // end of class GraphiteRadioButtonMenuItemUI
