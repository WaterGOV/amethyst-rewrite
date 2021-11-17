import org.apache.tools.ant.filters.ReplaceTokens

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

plugins {
    `java-library`
}

tasks.processResources {
    // this thing likes to bite me when i don't clean, force it to happen
    outputs.upToDateWhen { false }
    filter<ReplaceTokens>("tokens" to mapOf("version" to project.version))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(project(":amethyst-bom")))
    implementation(project(":amethyst-api"))

    implementation("com.google.code.gson:gson")
    implementation("org.spongepowered:configurate-gson")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
}