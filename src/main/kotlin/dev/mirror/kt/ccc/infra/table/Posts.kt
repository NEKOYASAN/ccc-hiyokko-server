package dev.mirror.kt.ccc.infra.table

import org.jetbrains.exposed.dao.id.UUIDTable

object Posts: UUIDTable() {
    val thread = reference("thread", Threads)
    val author = varchar("author", 39) // IPv6の最長文字数が39
    val content = varchar("content", 500)
}
