package dev.mirror.kt.ccc.infra.table

import org.jetbrains.exposed.dao.id.UUIDTable

object Threads : UUIDTable() {
    val title = varchar("title", 20)
    val author = varchar("author", 39) // IPv6の最長文字数
}
