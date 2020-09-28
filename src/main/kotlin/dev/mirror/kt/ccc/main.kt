package dev.mirror.kt.ccc

import dev.mirror.kt.ccc.auth.IpAddressPrincipal
import dev.mirror.kt.ccc.auth.ipAddress
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.authenticate
import io.ktor.auth.authentication
import io.ktor.routing.routing
import org.koin.ktor.ext.Koin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
fun Application.module() {
    install(Koin)

    authentication {
        ipAddress {
            validate { IpAddressPrincipal(it.ipAddress) }
        }
    }

    routing {
        authenticate {
            installApi()
        }
    }
}
