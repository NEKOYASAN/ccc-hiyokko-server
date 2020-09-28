package dev.mirror.kt.ccc.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

@Serializable
data class Thread(
    val author: String,
    val title: String
)

fun Thread.toJsonObject() = buildJsonObject {
    put("author", author)
    put("title", title)
}

@Serializable
data class Post(
    val author: String,
    val content: String
)

fun Post.toJsonObject() = buildJsonObject {
    put("author", author)
    put("content", content)
}

@Serializable
enum class RequestMethod {
    @SerialName("new_thread")
    NEW_THREAD,

    @SerialName("change_thread")
    CHANGE_THREAD,

    @SerialName("new_post")
    NEW_POST,
}

@Serializable
data class WebSocketRequest(
    val method: RequestMethod,
    val arguments: JsonObject
)

@Serializable
enum class ResponseStatus {
    @SerialName("success")
    SUCCESS,

    @SerialName("error")
    ERROR,
}

@Serializable
data class WebSocketResponse(
    val status: ResponseStatus,
    val type: String,
    val data: JsonObject
)

@Serializable
data class BasicResponseContent(
    val content: String
)

fun BasicResponseContent.toJsonObject() = buildJsonObject {
    put("content", content)
}
