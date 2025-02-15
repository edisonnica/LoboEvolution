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
package com.jtattoo.plaf.texture;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.BaseSliderUI;

/**
 * <p>TextureSliderUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureSliderUI extends BaseSliderUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new TextureSliderUI((JSlider) c);
	}

	/**
	 * <p>Constructor for TextureSliderUI.</p>
	 *
	 * @param slider a {@link javax.swing.JSlider} object.
	 */
	public TextureSliderUI(JSlider slider) {
		super(slider);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBackground(Graphics g, JComponent c) {
		if (c.isOpaque()) {
			Component parent = c.getParent();
			if (parent != null && parent.getBackground() instanceof ColorUIResource) {
				TextureUtils.fillComponent(g, c, TextureUtils.BACKGROUND_TEXTURE_TYPE);
			} else {
				if (parent != null) {
					g.setColor(parent.getBackground());
				} else {
					g.setColor(c.getBackground());
				}
				g.fillRect(0, 0, c.getWidth(), c.getHeight());
			}
		}
	}

} // TextureSliderUI
