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

package net.lucypoulton.amethyst.api;

import net.kyori.adventure.text.Component;
import net.lucypoulton.amethyst.api.audience.AmethystUser;
import net.lucypoulton.squirtgun.command.node.CommandNode;
import net.lucypoulton.squirtgun.util.SemanticVersion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * A module.
 */
public interface AmethystModule {

    /**
     * Gets this module's name.
     */
    @NotNull String name();

    /**
     * Gets this module's version.
     */
    @NotNull SemanticVersion version();

    /**
     * Gets this module's authors.
     */
    @NotNull String[] authors();

    /**
     * Reloads this module.
     */
    void reload();

    /**
     * Prepares this module for disposal.
     */
    void dispose();

    /**
     * Gets the commands that this module exposes.
     */
    default @NotNull List<CommandNode<? super AmethystUser>> commands() {
        return List.of();
    }

    /**
     * Parses a placeholder string to a component.
     *
     * @param in   the stripped placeholder to parse. For example, the full placeholder %amethyst_core_displayname%
     *             will be provided as "displayname".
     * @param user a user for the placeholders to target. This parameter may be null if the placeholder requested is
     *             not player-specific.
     * @return a   component containing the parsed placeholder, or null if this placeholder is invalid or a
     *             player-specific placeholder has been requested when user is null
     * @since 1.0.0
     */
    @Nullable
    Component parsePlaceholder(String in, @Nullable AmethystUser user);
}