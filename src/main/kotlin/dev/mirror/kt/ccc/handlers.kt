package dev.mirror.kt.ccc

import dev.mirror.kt.ccc.auth.IpAddressPrincipal
import dev.mirror.kt.ccc.domain.RequestMethod
import dev.mirror.kt.ccc.domain.ResponseStatus
import dev.mirror.kt.ccc.domain.service.ThreadService
import dev.mirror.kt.ccc.domain.toJsonObject
import io.ktor.auth.authentication
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.websocket.DefaultWebSocketServerSession
import io.ktor.websocket.webSocket
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.koin.core.context.KoinContextHandler
import java.util.UUID

private const val CURRENT_THREAD_KEY = "current_thread"

private val threadCaches = mutableMapOf<DefaultWebSocketServerSession, UUID>()

fun Route.installApi() {
    webSocket("/") {
        for (frame in incoming) {
            if (frame !is Frame.Text) continue

            val req = frame.readText().parseRequest()
            when (req.method) {
                RequestMethod.NEW_THREAD -> newThread(req.arguments)
                RequestMethod.CHANGE_THREAD -> changeThread(req.arguments)
                RequestMethod.NEW_POST -> newPost(req.arguments)
            }
        }
    }
}

suspend fun DefaultWebSocketServerSession.newThread(arguments: JsonObject) {
    val title = arguments["title"]?.jsonPrimitive?.content
    if (title == null) {
        response(ResponseStatus.ERROR, "new_thread", "Title is required.")
        return
    }
    val author = call.authentication.principal<IpAddressPrincipal>()?.ipAddress ?: return

    val service by inject<ThreadService>()
    val thread = service.newThread(author, title)
    response(ResponseStatus.SUCCESS, "new_thread", thread.toJsonObject())
}

suspend fun DefaultWebSocketServerSession.changeThread(arguments: JsonObject) {
    val threadId = arguments["thread_id"]?.jsonPrimitive?.content
        ?.let { UUID.fromString(it) }
    if (threadId == null) {
        response(ResponseStatus.ERROR, "change_thread", "Invalid thread id.")
        return
    }
    call.sessions.set(CURRENT_THREAD_KEY, threadId)
    threadCaches[this] = threadId
}

suspend fun DefaultWebSocketServerSession.newPost(arguments: JsonObject) {
    val author = call.authentication.principal<IpAddressPrincipal>()?.ipAddress ?: return
    val content = arguments["content"]?.jsonPrimitive?.content ?: return
    val currentThreadId = call.sessions.get<UUID>()
    if (currentThreadId == null) {
        response(ResponseStatus.ERROR, "new_post", "The subscribed thread was not found")
        return
    }

    val service by inject<ThreadService>()
    val post = service.newPost(currentThreadId, author, content)
    if (post != null) {
        response(ResponseStatus.SUCCESS, "new_post", post.toJsonObject())
        threadCaches.filterValues { it == currentThreadId }
            .map { it.key }
            .forEach { session ->
                response(session, ResponseStatus.SUCCESS, "receive_new_post", post.toJsonObject())
            }
    } else {
        response(ResponseStatus.ERROR, "new_post", "Thread not found.")
    }
}

inline fun <reified T> inject() = KoinContextHandler.get().inject<T>()
