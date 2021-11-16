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

package net.lucypoulton.amethyst.core;

import net.lucypoulton.amethyst.api.platform.AmethystPlatform;
import net.lucypoulton.squirtgun.platform.event.EventHandler;
import net.lucypoulton.squirtgun.platform.event.PluginReloadEvent;
import net.lucypoulton.squirtgun.plugin.SquirtgunPlugin;
import net.lucypoulton.squirtgun.util.SemanticVersion;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AmethystPlugin extends SquirtgunPlugin<AmethystPlatform> {
    public AmethystPlugin(@NotNull CoreAmethystPlatform platform) {
        super(platform);
        try {
            platform.setDataPath(platform.getConfigPath(this).resolve("data"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onEnable() {
        getPlatform().getEventManager().register(
            EventHandler.executes(PluginReloadEvent.class, x -> getPlatform().getModuleManager().reloadModules())
        );
    }

    @Override
    public @NotNull String getPluginName() {
        return "Amethyst";
    }

    @Override
    public @NotNull SemanticVersion getPluginVersion() {
        return SemanticVersion.parse("1.0.0-SNAPSHOT");
    }

    @Override
    public @NotNull String[] getAuthors() {
        return new String[]{"Lucy Poulton"};
    }

    @Override
    public void reload() {
        super.reload();
    }
}
