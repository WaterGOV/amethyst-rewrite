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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

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
        platform.getLogger().info("Loading module " + clazz.getSimpleName());
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
        platform.getLogger().info("Unloading module " + module.getPluginName());
        module.onDisable();
        // TODO - unregister commands
        loadedModuleMap.remove(module.getClass(), module);
        platform.getLogger().info("Unloaded module " + module.getPluginName());
    }

    /**
     * Uses hacky reflection magic to load jars at runtime.
     */
    @Override
    public void loadFromPath(Path path) throws IOException {
        if (!Files.exists(path)) {
            platform.getLogger().warning("Modules directory does not exist. No modules loaded");
            return;
        }
        if (Files.isDirectory(path)) {
            Files.list(path).forEach(x -> {
                try {
                    loadFromPath(x);
                } catch (Exception e) {
                    platform.getLogger().warning("While loading a module: " + e.getMessage());
                    e.printStackTrace();
                }
            });
            return;
        }

        final URL jar;
        try {
            jar = path.toUri().toURL();
        } catch (MalformedURLException e) {
            // this should really never happen
            e.printStackTrace();
            return;
        }

        final URLClassLoader loader = new URLClassLoader(new URL[]{jar}, AmethystModule.class.getClassLoader());

        try (final JarInputStream stream = new JarInputStream(jar.openStream())) {
            JarEntry entry;
            while ((entry = stream.getNextJarEntry()) != null) {
                final String name = entry.getName();
                if (!name.endsWith(".class") || name.startsWith("META-INF")) {
                    continue;
                }

                final String className = name.substring(0, name.lastIndexOf('.')).replace('/', '.');
                try {
                    final Class<?> loaded = loader.loadClass(className);
                    if (AmethystModule.class.isAssignableFrom(loaded)) {
                        loadModule(loaded.asSubclass(AmethystModule.class));
                    }
                } catch (final NoClassDefFoundError ignored) {
                    platform.getLogger().warning("Failed to load class '" + className + "'");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new ModuleInitException("Error while loading class " + path.getFileName(), e);
        }
    }
}
