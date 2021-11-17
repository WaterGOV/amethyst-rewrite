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

package net.lucypoulton.amethyst.core.data;

import net.lucypoulton.amethyst.api.data.DataManager;
import net.lucypoulton.amethyst.api.data.DataStore;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;


/**
 * TODO - support for mysql
 */
public class CoreDataManager implements DataManager {

    private final Path rootPath;

    public CoreDataManager(Path rootPath) throws IOException {
        this.rootPath = rootPath;

        Files.createDirectories(rootPath.resolve("players"));
    }

    @Override
    public DataStore forPlayer(UUID uuid) {
        try {
            return new GsonDataStore(rootPath.resolve("players").resolve(uuid + ".json"));
        } catch (IOException e) {
            return null;
        }
    }

    // TODO
    @Override
    public DataStore globalConfig() {
        return null;
    }

    // TODO
    @Override
    public DataStore globalData() {
        return null;
    }
}
