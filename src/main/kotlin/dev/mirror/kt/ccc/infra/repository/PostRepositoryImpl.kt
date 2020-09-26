package dev.mirror.kt.ccc.infra.repository

import dev.mirror.kt.ccc.domain.Post
import dev.mirror.kt.ccc.domain.repository.PostRepository
import dev.mirror.kt.ccc.infra.entity.PostEntity
import dev.mirror.kt.ccc.infra.entity.ThreadEntity
import dev.mirror.kt.ccc.infra.toDomain
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class PostRepositoryImpl : PostRepository {
    override fun newPost(threadId: UUID, author: String, content: String): Post = transaction {
        ThreadEntity.findById(threadId)
            ?.let { thread ->
                PostEntity.new(UUID.randomUUID()) {
                    this.thread = thread
                    this.author = author
                    this.content = content
                }
            }
            ?: throw IllegalArgumentException("Thread not found.")
    }.toDomain()
}
