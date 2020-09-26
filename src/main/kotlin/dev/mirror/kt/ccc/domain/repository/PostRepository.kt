package dev.mirror.kt.ccc.domain.repository

import dev.mirror.kt.ccc.domain.Post
import java.util.UUID

interface PostRepository {
    fun newPost(threadId: UUID, author: String, content: String): Post
}
