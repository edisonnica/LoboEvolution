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
package org.loboevolution.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * <p>JavaConstructorObject class.</p>
 *
 *
 *
 */
public class JavaConstructorObject extends ScriptableObject implements Function {

	public static class SimpleInstantiator implements JavaInstantiator {
		private final JavaClassWrapper classWrapper;

		public SimpleInstantiator(final JavaClassWrapper classWrapper) {
			super();
			this.classWrapper = classWrapper;
		}

		@Override
		public Object newInstance() throws InstantiationException, IllegalAccessException {
			return this.classWrapper.newInstance();
		}
	}

	private static final long serialVersionUID = 1L;
	private final JavaClassWrapper classWrapper;
	private final JavaInstantiator instantiator;

	private final String name;

	/**
	 * <p>Constructor for JavaConstructorObject.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param classWrapper a {@link org.loboevolution.js.JavaClassWrapper} object.
	 */
	public JavaConstructorObject(String name, JavaClassWrapper classWrapper) {
		this.name = name;
		this.classWrapper = classWrapper;
		this.instantiator = new SimpleInstantiator(classWrapper);
	}

	/**
	 * <p>Constructor for JavaConstructorObject.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param classWrapper a {@link org.loboevolution.js.JavaClassWrapper} object.
	 * @param instantiator a {@link org.loboevolution.js.JavaInstantiator} object.
	 */
	public JavaConstructorObject(String name, JavaClassWrapper classWrapper, JavaInstantiator instantiator) {
		this.name = name;
		this.classWrapper = classWrapper;
		this.instantiator = instantiator;
	}

	/** {@inheritDoc} */
	@Override
	public Object call(Context cx, Scriptable scope, Scriptable thisObj, Object[] args) {
		throw new UnsupportedOperationException();
	}

	/** {@inheritDoc} */
	@Override
	public Scriptable construct(Context cx, Scriptable scope, Object[] args) {
		try {
			final Object javaObject = this.instantiator.newInstance();
			final Scriptable newObject = new JavaObjectWrapper(this.classWrapper, javaObject);
			newObject.setParentScope(scope);
			return newObject;
		} catch (final Exception err) {
			throw new IllegalStateException(err.getMessage());
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getClassName() {
		return this.name;
	}

	/** {@inheritDoc} */
	@Override
	public java.lang.Object getDefaultValue(java.lang.Class hint) {
		if (String.class.equals(hint)) {
			return "function " + this.name;
		} else {
			return super.getDefaultValue(hint);
		}
	}
}
