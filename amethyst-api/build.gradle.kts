plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    api(platform(project(":amethyst-bom")))

    api("net.lucypoulton:squirtgun-api")
    api("org.spongepowered:configurate-core")
    api("net.kyori:adventure-api")
    compileOnlyApi("org.jetbrains:annotations")
}