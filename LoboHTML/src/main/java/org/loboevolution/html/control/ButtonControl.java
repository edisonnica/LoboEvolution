/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.control;

import org.loboevolution.common.WrapperLayout;
import org.loboevolution.html.dom.domimpl.HTMLButtonElementImpl;

/**
 * <p>ButtonControl class.</p>
 *
 *
 *
 */
public class ButtonControl extends BaseControl implements UIControl {

private static final long serialVersionUID = 1L;
	
	private final HTMLButtonElementImpl  modelNode;
	
	/**
	 * <p>Constructor for ButtonControl.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLButtonElementImpl} object.
	 */
	public ButtonControl(HTMLButtonElementImpl modelNode) {
		super(modelNode);
		setLayout(WrapperLayout.getInstance());
		this.modelNode = modelNode;
	}

	/** {@inheritDoc} */
	@Override
	public void reset(final int availWidth, final int availHeight) {
		super.reset(availWidth, availHeight);
		modelNode.draw(this);
	}
}
