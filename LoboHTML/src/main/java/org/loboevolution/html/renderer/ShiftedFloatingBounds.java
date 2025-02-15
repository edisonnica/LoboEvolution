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
package org.loboevolution.html.renderer;

import java.util.Objects;

class ShiftedFloatingBounds implements FloatingBounds {
	private final FloatingBounds prevBounds;
	private final int shiftLeft;
	private final int shiftRight;
	private final int shiftY;

	/**
	 * Constructs the ShiftedFloatingBounds. Floatinb bounds moved up the hierarchy
	 * of renderables will generally have positive shifts.
	 *
	 * @param prevBounds The baseline floating bounds.
	 * @param shiftY     How much the original bounds have shifted in the Y axis.
	 * @param shiftLeft a int.
	 * @param shiftRight a int.
	 */
	public ShiftedFloatingBounds(final FloatingBounds prevBounds, final int shiftLeft, final int shiftRight,
			final int shiftY) {
		super();
		this.prevBounds = prevBounds;
		this.shiftLeft = shiftLeft;
		this.shiftRight = shiftRight;
		this.shiftY = shiftY;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(final Object obj) {
		// Important for layout caching.
		if (!(obj instanceof ShiftedFloatingBounds)) {
			return false;
		}
		final ShiftedFloatingBounds other = (ShiftedFloatingBounds) obj;
		return this.shiftY == other.shiftY && this.shiftLeft == other.shiftLeft && this.shiftRight == other.shiftRight
				&& Objects.equals(this.prevBounds, other.prevBounds);
	}

	/** {@inheritDoc} */
	@Override
	public int getClearY(int y) {
		return this.prevBounds.getClearY(y - this.shiftY) + this.shiftY;
	}

	/** {@inheritDoc} */
	@Override
	public int getFirstClearY(int y) {
		return this.prevBounds.getFirstClearY(y - this.shiftY) + this.shiftY;
	}

	/** {@inheritDoc} */
	@Override
	public int getLeft(int y) {
		return this.prevBounds.getLeft(y - this.shiftY) + this.shiftLeft;
	}

	/** {@inheritDoc} */
	@Override
	public int getLeftClearY(int y) {
		return this.prevBounds.getLeftClearY(y - this.shiftY) + this.shiftY;
	}

	/** {@inheritDoc} */
	@Override
	public int getMaxY() {
		return this.prevBounds.getMaxY() + this.shiftY;
	}

	/** {@inheritDoc} */
	@Override
	public int getRight(int y) {
		return this.prevBounds.getRight(y - this.shiftY) + this.shiftRight;
	}

	/** {@inheritDoc} */
	@Override
	public int getRightClearY(int y) {
		return this.prevBounds.getRightClearY(y - this.shiftY) + this.shiftY;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		return this.shiftY ^ this.shiftLeft ^ this.shiftRight;
	}
}
