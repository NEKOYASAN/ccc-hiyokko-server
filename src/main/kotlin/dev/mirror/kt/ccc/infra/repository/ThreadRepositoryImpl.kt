package dev.mirror.kt.ccc.infra.repository

import dev.mirror.kt.ccc.domain.Thread
import dev.mirror.kt.ccc.domain.repository.ThreadRepository
import dev.mirror.kt.ccc.infra.entity.ThreadEntity
import dev.mirror.kt.ccc.infra.toDomain
import org.jetbrains.exposed.sql.transactions.transaction

class ThreadRepositoryImpl : ThreadRepository {
    override fun newThread(author: String, title: String): Thread = transaction {
        ThreadEntity.new {
            this.author = author
            this.title = title
        }
    }.toDomain()
}
