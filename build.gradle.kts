plugins {
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.serialization") version "1.4.10"
}

group = "dev.mirror-kt.ccc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

object Ktor {
    const val version = "1.4.0"
}

object Exposed {
    const val version = "0.27.1"
}

object MySQL {
    const val version = "8.0.21"
}

object Serialization {
    const val version = "1.0.0-RC2"
}

object Koin {
    const val version = "2.1.6"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-server-netty:${Ktor.version}")
    implementation("io.ktor:ktor-auth:${Ktor.version}")
    implementation("io.ktor:ktor-websockets:${Ktor.version}")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Serialization.version}")

    implementation("org.jetbrains.exposed:exposed-core:${Exposed.version}")
    implementation("org.jetbrains.exposed:exposed-jdbc:${Exposed.version}")
    implementation("org.jetbrains.exposed:exposed-dao:${Exposed.version}")
    runtimeOnly("mysql:mysql-connector-java:${MySQL.version}")

    implementation("org.koin:koin-ktor:${Koin.version}")
}
