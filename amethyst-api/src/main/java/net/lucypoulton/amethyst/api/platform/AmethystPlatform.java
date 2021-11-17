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

package net.lucypoulton.amethyst.api.platform;

import net.kyori.adventure.text.Component;
import net.lucypoulton.amethyst.api.audience.AmethystPlayer;
import net.lucypoulton.amethyst.api.data.DataStore;
import net.lucypoulton.squirtgun.format.FormatProvider;
import net.lucypoulton.squirtgun.platform.Platform;
import net.lucypoulton.squirtgun.platform.audience.SquirtgunPlayer;
import net.lucypoulton.squirtgun.util.SemanticVersion;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface AmethystPlatform extends Platform {
    /**
     * Gets the module manager.
     */
    ModuleManager getModuleManager();

    /**
     * Gets the plugin's global configuration.
     */
    DataStore getConfig();

    @Override
    AmethystPlayer getPlayer(String name);

    @Override
    AmethystPlayer getPlayer(UUID uuid);

    /**
     * Gets a {@link SquirtgunPlayer} as an {@link AmethystPlayer}.
     */
    AmethystPlayer asAmethystPlayer(SquirtgunPlayer player);

    /**
     * Gets a global data store that will be shared across any servers running on the same database. This store should
     * be used to store persistent data, unrelated to config. Data should not be specific to any particular player -
     * use {@link AmethystPlayer#getDataStore()} for this purpose.
     *
     * @return a global data store
     */
    DataStore getDataStore();

    /**
     * Parses a string containing amethyst (not PlaceholderAPI) placeholders that will get parsed to components.
     * Any invalid placeholders will be ignored and included as-is.
     *
     * @param input the string to process
     * @param user  a user for the placeholders to target, if not needed for the placeholder, then null
     * @return a Component, possibly containing extra data, including the parsed placeholders
     */
    Component parsePlaceholders(String input, @Nullable AmethystPlayer user);

    /**
     * Gets the plugin's global {@link FormatProvider} to ensure everything looks uniform.
     */
    FormatProvider format();

    /**
     * Gets the Amethyst core's version.
     */
    SemanticVersion amethystVersion();
}

