package dev.mirror.kt.ccc.domain.repository

import dev.mirror.kt.ccc.domain.Thread

interface ThreadRepository {
    fun newThread(author: String, title: String): Thread
}
