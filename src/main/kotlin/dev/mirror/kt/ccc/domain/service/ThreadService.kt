package dev.mirror.kt.ccc.domain.service

import dev.mirror.kt.ccc.domain.Post
import dev.mirror.kt.ccc.domain.repository.PostRepository
import dev.mirror.kt.ccc.domain.repository.ThreadRepository
import java.util.UUID
import dev.mirror.kt.ccc.domain.Thread as DomainThread

class ThreadService(
    private val threadRepository: ThreadRepository,
    private val postRepository: PostRepository
) {
    fun newThread(author: String, title: String): DomainThread = threadRepository.newThread(author, title)
    fun newPost(threadId: UUID, author: String, content: String): Post? = runCatching {
        postRepository.newPost(threadId, author, content)
    }.getOrNull()
}
