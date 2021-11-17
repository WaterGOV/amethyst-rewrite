rootProject.name = "amethyst"

pluginManagement {
    plugins {
        id("com.github.johnrengelman.shadow") version "7.1.0"
        kotlin("jvm") version "1.5.31"
    }
}

include(
    "amethyst-bom",
    "amethyst-api",
    "amethyst-core",
    "amethyst-platform-bukkit",
    "amethyst-module-example"
)