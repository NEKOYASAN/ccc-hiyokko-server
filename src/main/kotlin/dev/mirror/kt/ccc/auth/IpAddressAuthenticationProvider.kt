package dev.mirror.kt.ccc.auth

import io.ktor.application.call
import io.ktor.auth.Authentication
import io.ktor.auth.AuthenticationFailedCause
import io.ktor.auth.AuthenticationFunction
import io.ktor.auth.AuthenticationPipeline
import io.ktor.auth.AuthenticationProvider
import io.ktor.features.origin
import io.ktor.http.HttpStatusCode
import io.ktor.request.ApplicationRequest
import io.ktor.response.respond

class IpAddressAuthenticationProvider (
    configuration: Configuration
) : AuthenticationProvider(configuration) {
    val authenticationFunction = configuration.authenticationFunction

    class Configuration(name: String?): AuthenticationProvider.Configuration(name) {
        internal var authenticationFunction: AuthenticationFunction<IpAddressCredentials> = {
            throw NotImplementedError("IpAddress auth validate function is not specified. use ipAddress { validate { ... } } to fix.")
        }

        fun validate(body: AuthenticationFunction<IpAddressCredentials>) {
            authenticationFunction = body
        }
    }
}

fun Authentication.Configuration.ipAddress(
    name: String? = null,
    configure: IpAddressAuthenticationProvider.Configuration.() -> Unit
) {
    val provider = IpAddressAuthenticationProvider(IpAddressAuthenticationProvider.Configuration(name).apply(configure))
    val authenticate = provider.authenticationFunction

    provider.pipeline.intercept(AuthenticationPipeline.RequestAuthentication) { context ->
        val credentials = call.request.ipAddressAuthenticationCredentials()
        val principal = credentials?.let { authenticate(call, it) }

        val cause = when {
            credentials == null -> AuthenticationFailedCause.NoCredentials
            principal == null -> AuthenticationFailedCause.InvalidCredentials
            else -> null
        }

        if(cause != null) {
            context.challenge(ipAddressAuthenticationChallengeKey, cause) {
                call.respond(HttpStatusCode.Unauthorized, "Unknown ip address.")
                it.complete()
            }
        }

        if (principal != null) {
            context.principal(principal)
        }
    }

    register(provider)
}

fun ApplicationRequest.ipAddressAuthenticationCredentials(): IpAddressCredentials? {
    val address = origin.remoteHost
    return IpAddressCredentials(address)
}

private val ipAddressAuthenticationChallengeKey: Any = "IpAddressAuth"
