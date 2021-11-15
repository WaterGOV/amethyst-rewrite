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

import net.lucypoulton.amethyst.api.AmethystModule;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A key for a value in a {@link DataStore}.
 *
 * @param <T> the type of the value at this key
 * @author lucy
 * @since 1.0.0
 */
public class DataKey<T extends Serializable> {
    private final String parent;
    private final String child;
    private final Class<T> clazz;

    String getParent() {
        return parent;
    }

    String getChild() {
        return child;
    }

    /**
     * Creates a new key.
     *
     * @param parent the module this key belongs to
     * @param child  the child string node
     * @param clazz  the class of the expected type
     * @since 1.0.0
     */
    public DataKey(AmethystModule parent, String child, Class<T> clazz) {
        this.parent = parent.name();
        this.child = child;
        this.clazz = clazz;
    }

    /**
     * Creates a new key. Avoid using this in favour of {@link DataKey#DataKey(AmethystModule, String, Class)}.
     *
     * @param parent the name of the module this key belongs to
     * @param child  the child string node
     * @param clazz  the class of the expected type
     * @since 1.0.0
     */
    public DataKey(String parent, String child, Class<T> clazz) {
        this.parent = parent;
        this.child = child;
        this.clazz = clazz;
    }

    /**
     * Gets this key's expected value type.
     *
     * @since 1.0.0
     */
    public Class<T> getValueType() {
        return clazz;
    }

    /**
     * Gets the name of this key, formatted "parent.child".
     *
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return this.parent + "." + this.child;
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent, child);
    }

    /**
     * Checks if this key is equal to another, comparing parent and children but not return type.
     *
     * @since 1.0.0
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DataKey<?>)) {
            return false;
        }
        DataKey<?> otherCasted = (DataKey<?>) other;
        return parent.equals(otherCasted.getParent())
            && child.equals(otherCasted.getChild());
    }
}
