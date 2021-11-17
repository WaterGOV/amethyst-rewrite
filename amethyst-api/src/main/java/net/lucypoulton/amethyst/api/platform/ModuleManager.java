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

import net.lucypoulton.amethyst.api.AmethystModule;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

public interface ModuleManager {
    /**
     * Gets the instance of a module.
     *
     * @param clazz the module class to retrieve
     * @return the module instance, or null if it's not registered
     * @since 1.0.0
     */
    <T extends AmethystModule> @Nullable T getModule(Class<T> clazz);

    /**
     * Gets the instance of a module. Case-insensitive.
     *
     * @param name the name of the module to retrieve
     * @return the module instance, or null if it's not registered
     * @since 1.0.0
     */
    @Nullable AmethystModule getModule(String name);

    /**
     * Gets all enabled modules.
     *
     * @since 1.0.0
     */
    Collection<AmethystModule> getLoadedModules();

    /**
     * Loads a module.
     *
     * @param clazz the class to instantiate
     * @throws ModuleInitException if an exception is thrown when the module is instantiated or enabled
     * @since 1.0.0
     */
    <T extends AmethystModule> void loadModule(Class<T> clazz) throws ModuleInitException;

    /**
     * Unloads a module, deregistering its commands and listeners.
     *
     * @param module the module to unload
     * @since 1.0.0
     */
    void unloadModule(AmethystModule module);

    /**
     * Loads all modules that can be discovered from JAR files in the given path.
     */
    void loadFromPath(Path path) throws IOException;

    /**
     * Calls {@link AmethystModule#reload()} on all loaded modules.
     *
     * @since 1.0.0
     */
    default void reloadModules() {
        for (AmethystModule module : getLoadedModules()) {
            module.reload();
        }
    }
}
