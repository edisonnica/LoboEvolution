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

package org.loboevolution.html.node.js;

/**
 * <p>WindowOrWorkerGlobalScope interface.</p>
 */
public interface WindowOrWorkerGlobalScope {

    /**
     * <p>setTimeout.</p>
     *
     * @param function a {@link org.mozilla.javascript.Function} object.
     * @return a {@link java.lang.Integer} object.
     */
    int setTimeout(final Object function);

    /**
     * <p>setTimeout.</p>
     *
     * @param function a {@link java.lang.Object} object.
     * @param millis a {@link java.lang.Double} object.
     * @return a {@link java.lang.Integer} object.
     */
    int setTimeout(final Object function, double millis);

    /**
     * <p>setInterval.</p>
     *
     * @param function a {@link java.lang.Object} object.
     * @param millis a {@link java.lang.Double} object.
     * @return a {@link java.lang.Integer} object.
     */
    int setInterval(final Object function, double millis);
}
