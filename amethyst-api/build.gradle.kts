plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    api(platform(project(":amethyst-bom")))
}