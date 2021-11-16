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

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import net.lucypoulton.amethyst.api.audience.AmethystPlayer;
import net.lucypoulton.amethyst.api.data.DataStore;
import net.lucypoulton.squirtgun.platform.Gamemode;
import net.lucypoulton.squirtgun.platform.audience.SquirtgunPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AmethystPlayerWrapper implements AmethystPlayer, ForwardingAudience.Single {

    private final SquirtgunPlayer wrapped;
    private final CoreAmethystPlatform platform;

    public AmethystPlayerWrapper(SquirtgunPlayer wrapped, CoreAmethystPlatform platform) {
        this.wrapped = wrapped;
        this.platform = platform;
    }

    @Override
    public @NotNull Audience audience() {
        return wrapped;
    }

    @Override
    public UUID getUuid() {
        return wrapped.getUuid();
    }

    @Override
    public String getUsername() {
        return wrapped.getUsername();
    }

    @Override
    public boolean isOnline() {
        return wrapped.isOnline();
    }

    @Override
    public boolean hasPermission(String permission) {
        return wrapped.hasPermission(permission);
    }

    @Override
    public Gamemode getGamemode() {
        return wrapped.getGamemode();
    }

    @Override
    public void setGamemode(Gamemode mode) {
        wrapped.setGamemode(mode);
    }

    @Override
    public Component displayName() {
        return null;
    }

    @Override
    public DataStore getDataStore() {
        return platform.getDataManager().forPlayer(getUuid());
    }
}
