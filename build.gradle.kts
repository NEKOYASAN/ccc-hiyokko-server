plugins {
    kotlin("jvm") version "1.4.0"
}

group = "dev.mirror-kt.ccc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

object Ktor {
    const val version = "1.4.0"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-netty:${Ktor.version}")
    implementation("io.ktor:ktor-auth:${Ktor.version}")
}
