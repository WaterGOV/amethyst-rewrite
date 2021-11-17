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

package net.lucypoulton.amethyst.core.module;

import net.kyori.adventure.text.Component;
import net.lucypoulton.amethyst.api.AmethystModule;
import net.lucypoulton.amethyst.api.platform.AmethystPlatform;
import net.lucypoulton.squirtgun.command.condition.Condition;
import net.lucypoulton.squirtgun.command.context.CommandContext;
import net.lucypoulton.squirtgun.command.node.AbstractNode;
import net.lucypoulton.squirtgun.format.FormatProvider;
import net.lucypoulton.squirtgun.platform.audience.PermissionHolder;
import org.jetbrains.annotations.Nullable;

public class VersionNode extends AbstractNode<PermissionHolder> {

    private final AmethystPlatform platform;

    public VersionNode(AmethystPlatform platform) {
        super("amethyst", "Shows the plugin's and loaded modules' versions", Condition.alwaysTrue());
        this.platform = platform;
    }

    @Override
    public @Nullable Component execute(CommandContext context) {
        final FormatProvider fmt = context.getFormat();
        Component out = fmt.formatMain("Amethyst version ")
            .append(fmt.formatAccent(platform.amethystVersion().toString()))
            .append(Component.newline());
        for (AmethystModule module : platform.getModuleManager().getLoadedModules()) {
            out = out.append(
                fmt.formatAccent(module.getPluginName())
                    .append(fmt.formatMain(" version "))
                    .append(fmt.formatAccent(module.getPluginVersion().toString()))
                    .append(Component.newline())
            );
        }
        return out;
    }
}
