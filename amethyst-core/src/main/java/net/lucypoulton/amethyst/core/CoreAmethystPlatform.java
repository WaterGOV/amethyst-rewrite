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

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.lucypoulton.amethyst.api.audience.AmethystPlayer;
import net.lucypoulton.amethyst.api.data.DataManager;
import net.lucypoulton.amethyst.api.data.DataStore;
import net.lucypoulton.amethyst.api.platform.AmethystPlatform;
import net.lucypoulton.amethyst.api.platform.ModuleManager;
import net.lucypoulton.amethyst.core.data.CoreDataManager;
import net.lucypoulton.squirtgun.command.node.CommandNode;
import net.lucypoulton.squirtgun.format.FormatProvider;
import net.lucypoulton.squirtgun.platform.AuthMode;
import net.lucypoulton.squirtgun.platform.Platform;
import net.lucypoulton.squirtgun.platform.audience.SquirtgunPlayer;
import net.lucypoulton.squirtgun.platform.audience.SquirtgunUser;
import net.lucypoulton.squirtgun.platform.event.EventManager;
import net.lucypoulton.squirtgun.platform.scheduler.TaskScheduler;
import net.lucypoulton.squirtgun.plugin.SquirtgunPlugin;
import net.lucypoulton.squirtgun.util.SemanticVersion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class CoreAmethystPlatform implements AmethystPlatform {

    private DataManager dataManager;
    private final ModuleManager manager;
    private final Platform parent;

    public CoreAmethystPlatform(Platform parent) {
        this.parent = parent;
        manager = new CoreModuleManager(this);
    }

    void setDataPath(Path path) throws IOException {
        dataManager = new CoreDataManager(path);
    }

    @Override
    public ModuleManager getModuleManager() {
        return manager;
    }

    @Override
    public DataStore getConfig() {
        return dataManager.globalConfig();
    }

    @Override
    public AmethystPlayer getPlayer(String name) {
        return asAmethystPlayer(parent.getPlayer(name));
    }

    @Override
    public AmethystPlayer getPlayer(UUID uuid) {
        return asAmethystPlayer(parent.getPlayer(uuid));
    }

    @Override
    public AmethystPlayer asAmethystPlayer(SquirtgunPlayer player) {
        return player instanceof AmethystPlayer ? (AmethystPlayer) player : new AmethystPlayerWrapper(player, this);
    }

    @Override
    public DataStore getDataStore() {
        return dataManager.globalData();
    }

    @Override
    public Component parsePlaceholders(String input, @Nullable AmethystPlayer user) {
        return null;
    }

    @Override
    public FormatProvider format() {
        // TODO
        return new FormatProvider() {
            @Override
            public Component formatMain(@NotNull String input, @NotNull TextDecoration[] formatters) {
                return Component.text(input);
            }

            @Override
            public Component formatAccent(@NotNull String input, @NotNull TextDecoration[] formatters) {
                return Component.text(input).color(TextColor.color(97, 178, 240));
            }

            @Override
            public Component getPrefix() {
                return Component.text("[Amethyst] ");
            }
        };
    }

    @Override
    public SemanticVersion amethystVersion() {
        return SemanticVersion.parse("1.0.0-SNAPSHOT"); // TODO delegate to buildsystem
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    @Override
    public List<SquirtgunPlayer> getOnlinePlayers() {
        return parent.getOnlinePlayers();
    }

    @Override
    public Path getConfigPath(SquirtgunPlugin<?> plugin) {
        return parent.getConfigPath(plugin);
    }

    @Override
    public void registerCommand(CommandNode<?> node, FormatProvider provider) {
        // TODO - separate this out for different platforms?
        parent.registerCommand(node, provider);
    }

    @Override
    public String name() {
        return "Amethyst (" + parent.name() + ")";
    }

    @Override
    public Logger getLogger() {
        return parent.getLogger();
    }

    @Override
    public void log(Component component) {
        parent.log(component);
    }

    @Override
    public AuthMode getAuthMode() {
        return parent.getAuthMode();
    }

    @Override
    public TaskScheduler getTaskScheduler() {
        return parent.getTaskScheduler();
    }

    @Override
    public EventManager getEventManager() {
        return parent.getEventManager();
    }

    @Override
    public SquirtgunUser getConsole() {
        return parent.getConsole();
    }

}
