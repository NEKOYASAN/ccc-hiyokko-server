package dev.mirror.kt.ccc.auth

import io.ktor.auth.Principal

data class IpAddressPrincipal(
    val ipAddress: String
) : Principal
