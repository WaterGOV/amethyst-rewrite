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
import net.lucypoulton.amethyst.api.audience.AmethystPlayer;
import net.lucypoulton.amethyst.api.data.DataManager;
import net.lucypoulton.amethyst.api.data.DataStore;
import net.lucypoulton.amethyst.api.platform.AmethystPlatform;
import net.lucypoulton.amethyst.api.platform.ModuleManager;
import net.lucypoulton.amethyst.core.data.CoreDataManager;
import net.lucypoulton.squirtgun.platform.audience.SquirtgunPlayer;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public abstract class CoreAmethystPlatform implements AmethystPlatform {

    private DataManager dataManager;

    void setDataPath(Path path) throws IOException {
        dataManager = new CoreDataManager(path);
    }

    /**
     * TODO
     */
    @Override
    public ModuleManager getModuleManager() {
        return null;
    }

    /**
     * TODO
     */
    @Override
    public DataStore getConfig() {
        return null;
    }

    /**
     * TODO
     */
    @Override
    public AmethystPlayer getPlayer(String name) {
        return null;
    }

    /**
     * TODO
     */
    @Override
    public AmethystPlayer getPlayer(UUID uuid) {
        return null;
    }

    /**
     * TODO
     */
    @Override
    public AmethystPlayer asAmethystPlayer(SquirtgunPlayer player) {
        return player instanceof AmethystPlayer ? (AmethystPlayer) player : new AmethystPlayerWrapper(player, this);
    }

    @Override
    public DataStore getDataStore() {
        return null;
    }

    @Override
    public Component parsePlaceholders(String input, @Nullable AmethystPlayer user) {
        return null;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
