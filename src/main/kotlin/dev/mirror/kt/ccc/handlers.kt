package dev.mirror.kt.ccc

import io.ktor.http.cio.websocket.Frame
import io.ktor.routing.Route
import io.ktor.websocket.webSocket

fun Route.installApi() {
    webSocket("/") {
        for(frame in incoming) {
            when(frame) {
                is Frame.Text -> {

                }
            }
        }
    }
}
