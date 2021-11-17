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

import net.lucypoulton.amethyst.api.AmethystModule;
import net.lucypoulton.amethyst.api.audience.AmethystPlayer;
import net.lucypoulton.amethyst.api.platform.AmethystPlatform;
import net.lucypoulton.squirtgun.command.condition.Condition;
import net.lucypoulton.squirtgun.command.node.CommandNode;
import net.lucypoulton.squirtgun.command.node.NodeBuilder;
import net.lucypoulton.squirtgun.command.node.subcommand.SubcommandNode;
import net.lucypoulton.squirtgun.platform.audience.PermissionHolder;
import net.lucypoulton.squirtgun.util.SemanticVersion;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CoreModule extends AmethystModule {

    private final CommandNode<PermissionHolder> node = SubcommandNode.withHelp(
        "amethyst",
        "Core commands for Amethyst",
        Condition.alwaysTrue(),
        new NodeBuilder<>()
            .name("version")
            .description("Shows the Amethyst core and modules' versions.")
            .condition(Condition.alwaysTrue())
            // TODO - show module versions here too
            .executes(ctx ->
                ctx.getFormat().formatMain("Amethyst version ")
                    .append(ctx.getFormat().formatAccent(this.getPlatform().amethystVersion().toString())))
            .build()
    );

    public CoreModule(@NotNull AmethystPlatform platform) {
        super(platform);
    }

    @Override
    public @NotNull String getPluginName() {
        return "Core";
    }

    @Override
    public @NotNull SemanticVersion getPluginVersion() {
        return getPlatform().amethystVersion();
    }

    @Override
    public @NotNull String[] getAuthors() {
        return new String[] {"Lucy Poulton"};
    }

    @Override
    public @NotNull List<CommandNode<? super AmethystPlayer>> commands() {
        return List.of(node);
    }
}
