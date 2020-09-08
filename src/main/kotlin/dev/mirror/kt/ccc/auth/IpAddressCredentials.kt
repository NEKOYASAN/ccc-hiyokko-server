package dev.mirror.kt.ccc.auth

import io.ktor.auth.Credential

data class IpAddressCredentials(val ipAddress: String) : Credential
