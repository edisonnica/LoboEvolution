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

import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.BaseInternalFrameUI;

/**
 * <p>GraphiteInternalFrameUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class GraphiteInternalFrameUI extends BaseInternalFrameUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new GraphiteInternalFrameUI((JInternalFrame) c);
	}

	/**
	 * <p>Constructor for GraphiteInternalFrameUI.</p>
	 *
	 * @param b a {@link javax.swing.JInternalFrame} object.
	 */
	public GraphiteInternalFrameUI(JInternalFrame b) {
		super(b);
	}

	/** {@inheritDoc} */
	@Override
	protected JComponent createNorthPane(JInternalFrame w) {
		titlePane = new GraphiteInternalFrameTitlePane(w);
		return titlePane;
	}

} // end of class GraphiteInternalFrameUI
