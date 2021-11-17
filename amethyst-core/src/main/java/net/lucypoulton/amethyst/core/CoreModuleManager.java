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

import net.lucypoulton.amethyst.api.AmethystModule;
import net.lucypoulton.amethyst.api.platform.AmethystPlatform;
import net.lucypoulton.amethyst.api.platform.ModuleInitException;
import net.lucypoulton.amethyst.api.platform.ModuleManager;
import net.lucypoulton.squirtgun.command.node.CommandNode;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CoreModuleManager implements ModuleManager {

    private final AmethystPlatform platform;

    private final Map<Class<? extends AmethystModule>, AmethystModule> loadedModuleMap = new HashMap<>();

    public CoreModuleManager(AmethystPlatform platform) {
        this.platform = platform;
    }

    @SuppressWarnings("unchecked") // safe due to logic when added
    @Override
    public <T extends AmethystModule> @Nullable T getModule(Class<T> clazz) {
        return (T) loadedModuleMap.get(clazz);
    }

    @Override
    public @Nullable AmethystModule getModule(String name) {
        return loadedModuleMap.values().stream()
            .filter(x -> x.getPluginName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    @Override
    public Collection<AmethystModule> getLoadedModules() {
        return loadedModuleMap.values();
    }

    @Override
    public <T extends AmethystModule> void loadModule(Class<T> clazz) throws ModuleInitException {
        platform.getLogger().info("Loading module" + clazz.getSimpleName());
        if (Modifier.isAbstract(clazz.getModifiers())) {
            throw new ModuleInitException("Module " + clazz.getName() + " is abstract");
        }
        try {
            T module = clazz.getDeclaredConstructor(AmethystPlatform.class).newInstance(platform);
            module.onEnable();
            for (CommandNode<?> node : module.commands()) {
                // TODO - migrate this method to Supplier<FormatProvider> so reloads work?
                platform.registerCommand(node, platform.format());
            }
            loadedModuleMap.put(clazz, module);

            platform.getLogger().info(String.format("Loaded module %s (%s)", module.getPluginName(), clazz.getSimpleName()));

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ModuleInitException("Exception while instantiating module " + clazz.getName(), e);
        } catch (NoSuchMethodException e) {
            throw new ModuleInitException("Module " + clazz.getName() + " did not expose a constructor with the correct signature");
        }
    }

    @Override
    public void unloadModule(AmethystModule module) {
        platform.getLogger().info("Unloading module" + module.getPluginName());
        module.onDisable();
        // TODO - unregister commands
        loadedModuleMap.remove(module.getClass(), module);
        platform.getLogger().info("Unloaded module" + module.getPluginName());
    }
}
