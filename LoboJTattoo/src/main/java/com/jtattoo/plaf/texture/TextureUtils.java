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
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.LazyImageIcon;

/**
 * <p>TextureUtils class.</p>
 *
 * Author Michael Hagen
 *
 */
public final class TextureUtils {

	/** Constant WINDOW_TEXTURE_TYPE=0 */
	public static final int WINDOW_TEXTURE_TYPE = 0;
	/** Constant BACKGROUND_TEXTURE_TYPE=1 */
	public static final int BACKGROUND_TEXTURE_TYPE = 1;
	/** Constant ALTER_BACKGROUND_TEXTURE_TYPE=2 */
	public static final int ALTER_BACKGROUND_TEXTURE_TYPE = 2;
	/** Constant SELECTED_TEXTURE_TYPE=3 */
	public static final int SELECTED_TEXTURE_TYPE = 3;
	/** Constant ROLLOVER_TEXTURE_TYPE=4 */
	public static final int ROLLOVER_TEXTURE_TYPE = 4;
	/** Constant PRESSED_TEXTURE_TYPE=5 */
	public static final int PRESSED_TEXTURE_TYPE = 5;
	/** Constant DISABLED_TEXTURE_TYPE=6 */
	public static final int DISABLED_TEXTURE_TYPE = 6;
	/** Constant MENUBAR_TEXTURE_TYPE=7 */
	public static final int MENUBAR_TEXTURE_TYPE = 7;
	/** Constant LAST_TEXTURE_TYPE=7 */
	public static final int LAST_TEXTURE_TYPE = 7;

	/** Constant WINDOW_TEXTURE_KEY="WindowTexture" */
	public static final String WINDOW_TEXTURE_KEY = "WindowTexture";
	/** Constant BACKGROUND_TEXTURE_KEY="BackgroundTexture" */
	public static final String BACKGROUND_TEXTURE_KEY = "BackgroundTexture";
	/** Constant ALTER_BACKGROUND_TEXTURE_KEY="AlterBackgroundTexture" */
	public static final String ALTER_BACKGROUND_TEXTURE_KEY = "AlterBackgroundTexture";
	/** Constant SELECTED_TEXTURE_KEY="SelectedTexture" */
	public static final String SELECTED_TEXTURE_KEY = "SelectedTexture";
	/** Constant ROLLOVER_TEXTURE_KEY="RolloverTexture" */
	public static final String ROLLOVER_TEXTURE_KEY = "RolloverTexture";
	/** Constant PRESSED_TEXTURE_KEY="PressedTexture" */
	public static final String PRESSED_TEXTURE_KEY = "PressedTexture";
	/** Constant DISABLED_TEXTURE_KEY="DisabledTexture" */
	public static final String DISABLED_TEXTURE_KEY = "DisabledTexture";
	/** Constant MENUBAR_TEXTURE_KEY="MenubarTexture" */
	public static final String MENUBAR_TEXTURE_KEY = "MenubarTexture";

	private static Icon windowTexture = null;
	private static Icon backgroundTexture = null;
	private static Icon alterBackgroundTexture = null;
	private static Icon selectedTexture = null;
	private static Icon rolloverTexture = null;
	private static Icon pressedTexture = null;
	private static Icon disabledTexture = null;
	private static Icon menubarTexture = null;

	/**
	 * <p>fillComponent.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c a {@link java.awt.Component} object.
	 * @param textureType a int.
	 */
	public static void fillComponent(Graphics g, Component c, int textureType) {
		JTattooUtilities.fillComponent(g, c, getTexture(textureType));
	}

	/**
	 * <p>fillComponent.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c a {@link java.awt.Component} object.
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 * @param textureType a int.
	 */
	public static void fillComponent(Graphics g, Component c, int x, int y, int w, int h, int textureType) {
		Graphics2D g2D = (Graphics2D) g;
		Shape savedClip = g2D.getClip();
		Area clipArea = new Area(new Rectangle2D.Double(x, y, w, h));
		if (savedClip != null) {
			clipArea.intersect(new Area(savedClip));
		}
		g2D.setClip(clipArea);
		Icon texture = getTexture(textureType);
		if (texture != null) {
			int tw = texture.getIconWidth();
			int th = texture.getIconHeight();
			Point p = JTattooUtilities.getRelLocation(c);
			int ys = y - p.y;
			while (ys < y + h) {
				int xs = x - p.x;
				while (xs < x + w) {
					texture.paintIcon(c, g, xs, ys);
					xs += tw;
				}
				ys += th;
			}
		} else {
			g.setColor(c.getBackground());
			g.fillRect(x, y, w, h);
		}
		g2D.setClip(savedClip);
	}

	/**
	 * <p>fillRect.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c a {@link java.awt.Component} object.
	 * @param x a int.
	 * @param y a int.
	 * @param w a int.
	 * @param h a int.
	 * @param textureType a int.
	 */
	public static void fillRect(Graphics g, Component c, int x, int y, int w, int h, int textureType) {
		Graphics2D g2D = (Graphics2D) g;
		Shape savedClip = g2D.getClip();
		Area clipArea = new Area(new Rectangle2D.Double(x, y, w, h));
		if (savedClip != null) {
			clipArea.intersect(new Area(savedClip));
		}
		g2D.setClip(clipArea);
		Icon texture = getTexture(textureType);
		if (texture != null) {
			int tw = texture.getIconWidth();
			int th = texture.getIconHeight();
			int ys = y;
			while (ys < y + h) {
				int xs = x;
				while (xs < x + w) {
					texture.paintIcon(c, g, xs, ys);
					xs += tw;
				}
				ys += th;
			}
		} else {
			g.setColor(c.getBackground());
			g.fillRect(x, y, w, h);
		}
		g2D.setClip(savedClip);
	}

	/**
	 * <p>getTexture.</p>
	 *
	 * @param textureType a int.
	 * @return a {@link javax.swing.Icon} object.
	 */
	public static Icon getTexture(int textureType) {
		Icon texture = null;
		switch (textureType) {
		case WINDOW_TEXTURE_TYPE:
			texture = windowTexture;
			break;
		case BACKGROUND_TEXTURE_TYPE:
			texture = backgroundTexture;
			break;
		case ALTER_BACKGROUND_TEXTURE_TYPE:
			texture = alterBackgroundTexture;
			break;
		case SELECTED_TEXTURE_TYPE:
			texture = selectedTexture;
			break;
		case ROLLOVER_TEXTURE_TYPE:
			texture = rolloverTexture;
			break;
		case PRESSED_TEXTURE_TYPE:
			texture = pressedTexture;
			break;
		case DISABLED_TEXTURE_TYPE:
			texture = disabledTexture;
			break;
		case MENUBAR_TEXTURE_TYPE:
			texture = menubarTexture;
			break;
		default:
			break;
		}
		return texture;
	}

	/**
	 * <p>getTextureType.</p>
	 *
	 * @param c a {@link javax.swing.JComponent} object.
	 * @return a int.
	 */
	public static int getTextureType(JComponent c) {
		int textureType = TextureUtils.BACKGROUND_TEXTURE_TYPE;
		Object textureProperty = c.getClientProperty("textureType");
		if (textureProperty instanceof Integer) {
			int bt = (Integer) textureProperty;
			if (bt >= 0 && bt <= TextureUtils.LAST_TEXTURE_TYPE) {
				textureType = bt;
			}
		}
		return textureType;
	}

	/**
	 * <p>setUpTextures.</p>
	 */
	public static void setUpTextures() {
		String textureSet = "Default";
		if (AbstractLookAndFeel.getTheme() != null) {
			textureSet = AbstractLookAndFeel.getTheme().getTextureSet();
		}
		if (textureSet.startsWith("Rock")) {
			windowTexture = new LazyImageIcon("texture/patterns/rock/window_texture.jpg");
			backgroundTexture = new LazyImageIcon("texture/patterns/rock/background_texture.jpg");
			alterBackgroundTexture = new LazyImageIcon("texture/patterns/rock/alter_background_texture.jpg");
			selectedTexture = new LazyImageIcon("texture/patterns/rock/window_texture.jpg");
			rolloverTexture = new LazyImageIcon("texture/patterns/rock/rollover_texture.jpg");
			pressedTexture = new LazyImageIcon("texture/patterns/rock/pressed_texture.jpg");
			disabledTexture = new LazyImageIcon("texture/patterns/rock/background_texture.jpg");
			menubarTexture = new LazyImageIcon("texture/patterns/rock/menubar_texture.jpg");
		} else if (textureSet.startsWith("Textile")) {
			windowTexture = new LazyImageIcon("texture/patterns/textile/window_texture.jpg");
			backgroundTexture = new LazyImageIcon("texture/patterns/textile/background_texture.jpg");
			alterBackgroundTexture = new LazyImageIcon("texture/patterns/textile/alter_background_texture.jpg");
			selectedTexture = new LazyImageIcon("texture/patterns/textile/window_texture.jpg");
			rolloverTexture = new LazyImageIcon("texture/patterns/textile/rollover_texture.jpg");
			pressedTexture = new LazyImageIcon("texture/patterns/textile/rollover_texture.jpg");
			disabledTexture = new LazyImageIcon("texture/patterns/textile/background_texture.jpg");
			menubarTexture = new LazyImageIcon("texture/patterns/textile/menubar_texture.jpg");
		} else if (textureSet.startsWith("Snow")) {
			windowTexture = new LazyImageIcon("texture/patterns/snow/window_texture.jpg");
			backgroundTexture = new LazyImageIcon("texture/patterns/snow/background_texture.jpg");
			alterBackgroundTexture = new LazyImageIcon("texture/patterns/snow/alter_background_texture.jpg");
			selectedTexture = new LazyImageIcon("texture/patterns/snow/selected_texture.jpg");
			rolloverTexture = new LazyImageIcon("texture/patterns/snow/rollover_texture.jpg");
			pressedTexture = new LazyImageIcon("texture/patterns/snow/pressed_texture.jpg");
			disabledTexture = new LazyImageIcon("texture/patterns/snow/background_texture.jpg");
			menubarTexture = new LazyImageIcon("texture/patterns/snow/window_texture.jpg");
		} else {
			windowTexture = new LazyImageIcon("texture/patterns/leather/window_texture.jpg");
			backgroundTexture = new LazyImageIcon("texture/patterns/leather/background_texture.jpg");
			alterBackgroundTexture = new LazyImageIcon("texture/patterns/leather/alter_background_texture.jpg");
			selectedTexture = new LazyImageIcon("texture/patterns/leather/window_texture.jpg");
			rolloverTexture = new LazyImageIcon("texture/patterns/leather/rollover_texture.jpg");
			pressedTexture = new LazyImageIcon("texture/patterns/leather/pressed_texture.jpg");
			disabledTexture = new LazyImageIcon("texture/patterns/leather/background_texture.jpg");
			menubarTexture = new LazyImageIcon("texture/patterns/leather/menubar_texture.jpg");
		}

		Icon texture = AbstractLookAndFeel.getTheme().getWindowTexture();
		if (texture != null) {
			windowTexture = texture;
		}
		texture = AbstractLookAndFeel.getTheme().getBackgroundTexture();
		if (texture != null) {
			backgroundTexture = texture;
		}
		texture = AbstractLookAndFeel.getTheme().getAlterBackgroundTexture();
		if (texture != null) {
			alterBackgroundTexture = texture;
		}
		texture = AbstractLookAndFeel.getTheme().getSelectedTexture();
		if (texture != null) {
			selectedTexture = texture;
		}
		texture = AbstractLookAndFeel.getTheme().getRolloverTexture();
		if (texture != null) {
			rolloverTexture = texture;
		}
		texture = AbstractLookAndFeel.getTheme().getPressedTexture();
		if (texture != null) {
			pressedTexture = texture;
		}
		texture = AbstractLookAndFeel.getTheme().getDisabledTexture();
		if (texture != null) {
			disabledTexture = texture;
		}
		texture = AbstractLookAndFeel.getTheme().getMenubarTexture();
		if (texture != null) {
			menubarTexture = texture;
		}
	}

	private TextureUtils() {
	}

} // end of class TextureUtils
