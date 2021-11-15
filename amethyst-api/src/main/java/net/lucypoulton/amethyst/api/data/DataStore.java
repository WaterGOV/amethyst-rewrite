/*
 * Copyright (c) 2021 Lucy Poulton.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.lucypoulton.amethyst.api.data;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * A key-value store.
 *
 * @author lucy
 * @since 1.0.0
 */
public interface DataStore {
    /**
     * Gets the value of a key.
     *
     * @param key the key to retrieve the value of
     * @return the value of that key, or null if no value has been set
     * @since 1.0.0
     */
    @Nullable <T extends Serializable> T getValue(DataKey<T> key);

    /**
     * Sets the value of a key.
     *
     * @param key   the key to set the value of
     * @param value the value to set, or null to clear it
     * @since 1.0.0
     */
    <T extends Serializable> void setValue(DataKey<T> key, T value);

    /**
     * Sets the value of a key if it isn't set already.
     *
     * @param key	the key to set the default for
     * @param value the default value to set
     * @since 1.0.0
     */
    <T extends Serializable> void setDefaultValue(DataKey<T> key, T value);
}
