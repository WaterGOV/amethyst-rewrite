plugins {
    `java-platform`
}

repositories {
    mavenCentral()
}

javaPlatform.allowDependencies()

val squirtgunVersion = "2.0.0-pre8"
val adventureVersion = "4.9.3"

dependencies {
    api("net.lucypoulton:squirtgun-api:$squirtgunVersion")
    api("net.kyori:adventure-api:$adventureVersion")
    api("org.jetbrains:annotations:20.1.0")
}