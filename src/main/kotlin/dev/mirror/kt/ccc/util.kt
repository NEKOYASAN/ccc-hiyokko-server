package dev.mirror.kt.ccc

import dev.mirror.kt.ccc.domain.BasicResponseContent
import dev.mirror.kt.ccc.domain.ResponseStatus
import dev.mirror.kt.ccc.domain.WebSocketRequest
import dev.mirror.kt.ccc.domain.WebSocketResponse
import dev.mirror.kt.ccc.domain.toJsonObject
import io.ktor.http.cio.websocket.Frame
import io.ktor.websocket.DefaultWebSocketServerSession
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

fun String.parseRequest(): WebSocketRequest =
    Json.decodeFromString(this)

private fun WebSocketResponse.toJson(): String = Json.encodeToString(this)

suspend fun response(session: DefaultWebSocketServerSession, status: ResponseStatus, type: String, data: JsonObject) {
    session.outgoing.send(Frame.Text(WebSocketResponse(status, type, data).toJson()))
}

@JvmName("websocketResponse")
suspend fun DefaultWebSocketServerSession.response(status: ResponseStatus, type: String, data: JsonObject) {
    response(this, status, type, data)
}

suspend fun DefaultWebSocketServerSession.response(status: ResponseStatus, type: String, content: String) {
    response(this, status, type, BasicResponseContent(content).toJsonObject())
}
