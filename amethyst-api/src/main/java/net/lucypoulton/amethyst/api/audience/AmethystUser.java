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

package net.lucypoulton.amethyst.api.audience;

import net.kyori.adventure.text.Component;
import net.lucypoulton.amethyst.api.data.DataStore;
import net.lucypoulton.squirtgun.platform.audience.SquirtgunPlayer;

public interface AmethystUser extends SquirtgunPlayer {

    /**
     * Gets the user's display name. This will vary depending on the enabled modules and the config, but may contain:
     * <ul>
     *     <li>nicknames</li>
     *     <li>prefixes and suffixes for ranks</li>
     *     <li>statistics</li>
     * </ul>
     *
     * @since 1.0.0
     */
    Component displayName();

    /**
     * Gets the user's global data store. This should be used to store player-specific data.
     *
     * @return the user's global data store
     * @since 1.0.0
     */
    DataStore getDataStore();
}
