/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
package org.mozilla.javascript;

import java.util.List;

/**
 * <p>NativeJavaList class.</p>
 *
 *
 *
 */
public class NativeJavaList extends NativeJavaObject {

    private List<Object> list;

    @SuppressWarnings("unchecked")
    /**
     * <p>Constructor for NativeJavaList.</p>
     *
     * @param scope a {@link org.mozilla.javascript.Scriptable} object.
     * @param list a {@link java.lang.Object} object.
     */
    public NativeJavaList(Scriptable scope, Object list) {
        super(scope, list, list.getClass());
        assert list instanceof List;
        this.list = (List<Object>) list;
    }

    /** {@inheritDoc} */
    @Override
    public String getClassName() {
        return "JavaList";
    }


    /** {@inheritDoc} */
    @Override
    public boolean has(String name, Scriptable start) {
        if (name.equals("length")) {
            return true;
        }
        return super.has(name, start);
    }

    /** {@inheritDoc} */
    @Override
    public boolean has(int index, Scriptable start) {
        if (isWithValidIndex(index)) {
            return true;
        }
        return super.has(index, start);
    }

    /** {@inheritDoc} */
    @Override
    public boolean has(Symbol key, Scriptable start) {
        if (SymbolKey.IS_CONCAT_SPREADABLE.equals(key)) {
            return true;
        }
        return super.has(key, start);
    }

    /** {@inheritDoc} */
    @Override
    public Object get(String name, Scriptable start) {
        if ("length".equals(name)) {
            return Integer.valueOf(list.size());
        }
        return super.get(name, start);
    }

    /** {@inheritDoc} */
    @Override
    public Object get(int index, Scriptable start) {
        if (isWithValidIndex(index)) {
            Context cx = Context.getCurrentContext();
            Object obj = list.get(index);
            if (cx != null) {
                return cx.getWrapFactory().wrap(cx, this, obj, obj.getClass());
            }
            return obj;
        }
        return Undefined.instance;
    }

    /** {@inheritDoc} */
    @Override
    public Object get(Symbol key, Scriptable start) {
        if (SymbolKey.IS_CONCAT_SPREADABLE.equals(key)) {
            return Boolean.TRUE;
        }
        return super.get(key, start);
    }

    /** {@inheritDoc} */
    @Override
    public void put(int index, Scriptable start, Object value) {
        if (isWithValidIndex(index)) {
            list.set(index, Context.jsToJava(value, Object.class));
            return;
        }
        super.put(index, start, value);
    }

    /** {@inheritDoc} */
    @Override
    public Object[] getIds() {
        List<?> list = (List<?>) javaObject;
        Object[] result = new Object[list.size()];
        int i = list.size();
        while (--i >= 0) {
            result[i] = Integer.valueOf(i);
        }
        return result;
    }

    private boolean isWithValidIndex(int index) {
        return index >= 0  && index < list.size();
    }
}
