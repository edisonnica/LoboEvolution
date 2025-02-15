/* -*- Mode: java; tab-width: 8; indent-tabs-mode: nil; c-basic-offset: 4 -*-
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.javascript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *Implementation of resizable array with focus on minimizing memory usage by storing few initial array elements in object fields. Can also be used as a stack.
 *
 *
 *
 */
public class ObjArray implements Serializable
{
    private static final long serialVersionUID = 4174889037736658296L;

    /**
     * <p>Constructor for ObjArray.</p>
     */
    public ObjArray() { }

    /**
     * <p>isSealed.</p>
     *
     * @return a boolean.
     */
    public final boolean isSealed()
    {
        return sealed;
    }

    /**
     * <p>seal.</p>
     */
    public final void seal()
    {
        sealed = true;
    }

    /**
     * <p>isEmpty.</p>
     *
     * @return a boolean.
     */
    public final boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * <p>size.</p>
     *
     * @return a int.
     */
    public final int size()
    {
        return size;
    }

    /**
     * <p>Setter for the field <code>size</code>.</p>
     *
     * @param newSize a int.
     */
    public final void setSize(int newSize)
    {
        if (newSize < 0) throw new IllegalArgumentException();
        if (sealed) throw onSeledMutation();
        int N = size;
        if (newSize < N) {
            for (int i = newSize; i != N; ++i) {
                setImpl(i, null);
            }
        } else if (newSize > N) {
            if (newSize > FIELDS_STORE_SIZE) {
                ensureCapacity(newSize);
            }
        }
        size = newSize;
    }

    /**
     * <p>get.</p>
     *
     * @param index a int.
     * @return a {@link java.lang.Object} object.
     */
    public final Object get(int index)
    {
        if (!(0 <= index && index < size)) throw onInvalidIndex(index, size);
        return getImpl(index);
    }

    /**
     * <p>set.</p>
     *
     * @param index a int.
     * @param value a {@link java.lang.Object} object.
     */
    public final void set(int index, Object value)
    {
        if (!(0 <= index && index < size)) throw onInvalidIndex(index, size);
        if (sealed) throw onSeledMutation();
        setImpl(index, value);
    }

    private Object getImpl(int index)
    {
        switch (index) {
            case 0: return f0;
            case 1: return f1;
            case 2: return f2;
            case 3: return f3;
            case 4: return f4;
        }
        return data[index - FIELDS_STORE_SIZE];
    }

    private void setImpl(int index, Object value)
    {
        switch (index) {
            case 0: f0 = value; break;
            case 1: f1 = value; break;
            case 2: f2 = value; break;
            case 3: f3 = value; break;
            case 4: f4 = value; break;
            default: data[index - FIELDS_STORE_SIZE] = value;
        }

    }

    /**
     * <p>indexOf.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @return a int.
     */
    public int indexOf(Object obj)
    {
        int N = size;
        for (int i = 0; i != N; ++i) {
            Object current = getImpl(i);
            if (current == obj || (current != null && current.equals(obj))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * <p>lastIndexOf.</p>
     *
     * @param obj a {@link java.lang.Object} object.
     * @return a int.
     */
    public int lastIndexOf(Object obj)
    {
        for (int i = size; i != 0;) {
            --i;
            Object current = getImpl(i);
            if (current == obj || (current != null && current.equals(obj))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * <p>peek.</p>
     *
     * @return a {@link java.lang.Object} object.
     */
    public final Object peek()
    {
        int N = size;
        if (N == 0) throw onEmptyStackTopRead();
        return getImpl(N - 1);
    }

    /**
     * <p>pop.</p>
     *
     * @return a {@link java.lang.Object} object.
     */
    public final Object pop()
    {
        if (sealed) throw onSeledMutation();
        int N = size;
        --N;
        Object top;
        switch (N) {
            case -1: throw onEmptyStackTopRead();
            case 0: top = f0; f0 = null; break;
            case 1: top = f1; f1 = null; break;
            case 2: top = f2; f2 = null; break;
            case 3: top = f3; f3 = null; break;
            case 4: top = f4; f4 = null; break;
            default:
                top = data[N - FIELDS_STORE_SIZE];
                data[N - FIELDS_STORE_SIZE] = null;
        }
        size = N;
        return top;
    }

    /**
     * <p>push.</p>
     *
     * @param value a {@link java.lang.Object} object.
     */
    public final void push(Object value)
    {
        add(value);
    }

    /**
     * <p>add.</p>
     *
     * @param value a {@link java.lang.Object} object.
     */
    public final void add(Object value)
    {
        if (sealed) throw onSeledMutation();
        int N = size;
        if (N >= FIELDS_STORE_SIZE) {
            ensureCapacity(N + 1);
        }
        size = N + 1;
        setImpl(N, value);
    }

    /**
     * <p>add.</p>
     *
     * @param index a int.
     * @param value a {@link java.lang.Object} object.
     */
    public final void add(int index, Object value)
    {
        int N = size;
        if (!(0 <= index && index <= N)) throw onInvalidIndex(index, N + 1);
        if (sealed) throw onSeledMutation();
        Object tmp;
        switch (index) {
            case 0:
                if (N == 0) { f0 = value; break; }
                tmp = f0; f0 = value; value = tmp;
            /* fall through */ case 1:
                if (N == 1) { f1 = value; break; }
                tmp = f1; f1 = value; value = tmp;
            /* fall through */ case 2:
                if (N == 2) { f2 = value; break; }
                tmp = f2; f2 = value; value = tmp;
            /* fall through */ case 3:
                if (N == 3) { f3 = value; break; }
                tmp = f3; f3 = value; value = tmp;
            /* fall through */ case 4:
                if (N == 4) { f4 = value; break; }
                tmp = f4; f4 = value; value = tmp;

                index = FIELDS_STORE_SIZE;
            /* fall through */ default:
                ensureCapacity(N + 1);
                if (index != N) {
                    System.arraycopy(data, index - FIELDS_STORE_SIZE,
                                     data, index - FIELDS_STORE_SIZE + 1,
                                     N - index);
                }
                data[index - FIELDS_STORE_SIZE] = value;
        }
        size = N + 1;
    }

    /**
     * <p>remove.</p>
     *
     * @param index a int.
     */
    public final void remove(int index)
    {
        int N = size;
        if (!(0 <= index && index < N)) throw onInvalidIndex(index, N);
        if (sealed) throw onSeledMutation();
        --N;
        switch (index) {
            case 0:
                if (N == 0) { f0 = null; break; }
                f0 = f1;
            /* fall through */ case 1:
                if (N == 1) { f1 = null; break; }
                f1 = f2;
            /* fall through */ case 2:
                if (N == 2) { f2 = null; break; }
                f2 = f3;
            /* fall through */ case 3:
                if (N == 3) { f3 = null; break; }
                f3 = f4;
            /* fall through */ case 4:
                if (N == 4) { f4 = null; break; }
                f4 = data[0];

                index = FIELDS_STORE_SIZE;
            /* fall through */ default:
                if (index != N) {
                    System.arraycopy(data, index - FIELDS_STORE_SIZE + 1,
                                     data, index - FIELDS_STORE_SIZE,
                                     N - index);
                }
                data[N - FIELDS_STORE_SIZE] = null;
        }
        size = N;
    }

    /**
     * <p>clear.</p>
     */
    public final void clear()
    {
        if (sealed) throw onSeledMutation();
        int N = size;
        for (int i = 0; i != N; ++i) {
            setImpl(i, null);
        }
        size = 0;
    }

    /**
     * <p>toArray.</p>
     *
     * @return an array of {@link java.lang.Object} objects.
     */
    public final Object[] toArray()
    {
        Object[] array = new Object[size];
        toArray(array, 0);
        return array;
    }

    /**
     * <p>toArray.</p>
     *
     * @param array an array of {@link java.lang.Object} objects.
     */
    public final void toArray(Object[] array)
    {
        toArray(array, 0);
    }

    /**
     * <p>toArray.</p>
     *
     * @param array an array of {@link java.lang.Object} objects.
     * @param offset a int.
     */
    public final void toArray(Object[] array, int offset)
    {
        int N = size;
        switch (N) {
            default:
                System.arraycopy(data, 0, array, offset + FIELDS_STORE_SIZE,
                                 N - FIELDS_STORE_SIZE);
            /* fall through */ case 5: array[offset + 4] = f4;
            /* fall through */ case 4: array[offset + 3] = f3;
            /* fall through */ case 3: array[offset + 2] = f2;
            /* fall through */ case 2: array[offset + 1] = f1;
            /* fall through */ case 1: array[offset + 0] = f0;
            /* fall through */ case 0: break;
        }
    }

    private void ensureCapacity(int minimalCapacity)
    {
        int required = minimalCapacity - FIELDS_STORE_SIZE;
        if (required <= 0) throw new IllegalArgumentException();
        if (data == null) {
            int alloc = FIELDS_STORE_SIZE * 2;
            if (alloc < required) {
                alloc = required;
            }
            data = new Object[alloc];
        } else {
            int alloc = data.length;
            if (alloc < required) {
                   if (alloc <= FIELDS_STORE_SIZE) {
                    alloc = FIELDS_STORE_SIZE * 2;
                } else {
                    alloc *= 2;
                }
                if (alloc < required) {
                    alloc = required;
                }
                Object[] tmp = new Object[alloc];
                if (size > FIELDS_STORE_SIZE) {
                    System.arraycopy(data, 0, tmp, 0,
                                     size - FIELDS_STORE_SIZE);
                }
                data = tmp;
            }
        }
    }

    private static RuntimeException onInvalidIndex(int index, int upperBound)
    {
        // \u2209 is "NOT ELEMENT OF"
        String msg = index+" \u2209 [0, "+upperBound+')';
        throw new IndexOutOfBoundsException(msg);
    }

    private static RuntimeException onEmptyStackTopRead()
    {
        throw new RuntimeException("Empty stack");
    }

    private static RuntimeException onSeledMutation()
    {
        throw new IllegalStateException("Attempt to modify sealed array");
    }

    private void writeObject(ObjectOutputStream os) throws IOException
    {
        os.defaultWriteObject();
        int N = size;
        for (int i = 0; i != N; ++i) {
            Object obj = getImpl(i);
            os.writeObject(obj);
        }
    }

    private void readObject(ObjectInputStream is)
        throws IOException, ClassNotFoundException
    {
        is.defaultReadObject(); // It reads size
        int N = size;
        if (N > FIELDS_STORE_SIZE) {
            data = new Object[N - FIELDS_STORE_SIZE];
        }
        for (int i = 0; i != N; ++i) {
            Object obj = is.readObject();
            setImpl(i, obj);
        }
    }

// Number of data elements
    private int size;

    private boolean sealed;

    private static final int FIELDS_STORE_SIZE = 5;
    private transient Object f0, f1, f2, f3, f4;
    private transient Object[] data;
}
