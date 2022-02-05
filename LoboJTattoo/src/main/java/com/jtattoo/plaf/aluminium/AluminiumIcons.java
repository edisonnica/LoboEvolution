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
package com.jtattoo.plaf.aluminium;

import com.mercuryred.render.interfaces.ui.Color;
import com.mercuryred.render.interfaces.ui.Insets;

import com.mercuryred.render.interfaces.uiplus.Icon;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseIcons;
import com.jtattoo.plaf.LazyImageIcon;

/**
 * <p>AluminiumIcons class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AluminiumIcons extends BaseIcons {

	/**
	 * <p>getCloseIcon.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.Icon} object.
	 */
	public static Icon getCloseIcon() {
		if (closeIcon == null) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				closeIcon = new MacCloseIcon();
			} else {
				Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				closeIcon = new BaseIcons.CloseSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(0, 0, 1, 0));
			}
		}
		return closeIcon;
	}

	/**
	 * <p>getIconIcon.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.Icon} object.
	 */
	public static Icon getIconIcon() {
		if (iconIcon == null) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				iconIcon = new MacIconIcon();
			} else {
				Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				iconIcon = new BaseIcons.IconSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(0, 0, 1, 0));
			}
		}
		return iconIcon;
	}

	/**
	 * <p>getMaxIcon.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.Icon} object.
	 */
	public static Icon getMaxIcon() {
		if (maxIcon == null) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				maxIcon = new MacMaxIcon();
			} else {
				Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				maxIcon = new BaseIcons.MaxSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(0, 0, 1, 0));
			}
		}
		return maxIcon;
	}

	/**
	 * <p>getMinIcon.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.Icon} object.
	 */
	public static Icon getMinIcon() {
		if (minIcon == null) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				minIcon = new MacMinIcon();
			} else {
				Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
				Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
				Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
				minIcon = new BaseIcons.MinSymbol(iconColor, iconShadowColor, iconRolloverColor,
						new Insets(0, 0, 1, 0));
			}
		}
		return minIcon;
	}

	/**
	 * <p>getSplitterDownArrowIcon.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.Icon} object.
	 */
	public static Icon getSplitterDownArrowIcon() {
		if (splitterDownArrowIcon == null) {
			splitterDownArrowIcon = new LazyImageIcon("aluminium/icons/SplitterDownArrow.gif");
		}
		return splitterDownArrowIcon;
	}

	/**
	 * <p>getSplitterLeftArrowIcon.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.Icon} object.
	 */
	public static Icon getSplitterLeftArrowIcon() {
		if (splitterLeftArrowIcon == null) {
			splitterLeftArrowIcon = new LazyImageIcon("aluminium/icons/SplitterLeftArrow.gif");
		}
		return splitterLeftArrowIcon;
	}

	/**
	 * <p>getSplitterRightArrowIcon.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.Icon} object.
	 */
	public static Icon getSplitterRightArrowIcon() {
		if (splitterRightArrowIcon == null) {
			splitterRightArrowIcon = new LazyImageIcon("aluminium/icons/SplitterRightArrow.gif");
		}
		return splitterRightArrowIcon;
	}

	/**
	 * <p>getSplitterUpArrowIcon.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.Icon} object.
	 */
	public static Icon getSplitterUpArrowIcon() {
		if (splitterUpArrowIcon == null) {
			splitterUpArrowIcon = new LazyImageIcon("aluminium/icons/SplitterUpArrow.gif");
		}
		return splitterUpArrowIcon;
	}

	/**
	 * <p>getThumbHorIcon.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.Icon} object.
	 */
	public static Icon getThumbHorIcon() {
		if (thumbHorIcon == null) {
			thumbHorIcon = new LazyImageIcon("aluminium/icons/thumb_hor.gif");
		}
		return thumbHorIcon;
	}

	/**
	 * <p>getThumbHorIconRollover.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.Icon} object.
	 */
	public static Icon getThumbHorIconRollover() {
		if (thumbHorIconRollover == null) {
			thumbHorIconRollover = new LazyImageIcon("aluminium/icons/thumb_hor_rollover.gif");
		}
		return thumbHorIconRollover;
	}

	/**
	 * <p>getThumbVerIcon.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.Icon} object.
	 */
	public static Icon getThumbVerIcon() {
		if (thumbVerIcon == null) {
			thumbVerIcon = new LazyImageIcon("aluminium/icons/thumb_ver.gif");
		}
		return thumbVerIcon;
	}

	/**
	 * <p>getThumbVerIconRollover.</p>
	 *
	 * @return a {@link com.mercuryred.render.interfaces.uiplus.Icon} object.
	 */
	public static Icon getThumbVerIconRollover() {
		if (thumbVerIconRollover == null) {
			thumbVerIconRollover = new LazyImageIcon("aluminium/icons/thumb_ver_rollover.gif");
		}
		return thumbVerIconRollover;
	}

} // end of class AluminiumIcons
