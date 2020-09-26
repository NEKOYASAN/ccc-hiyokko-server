package dev.mirror.kt.ccc.infra.entity

import dev.mirror.kt.ccc.infra.table.Posts
import dev.mirror.kt.ccc.infra.table.Threads
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class ThreadEntity(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<ThreadEntity>(Threads)

    var author by Threads.author
    var title by Threads.title
    val posts by PostEntity referrersOn  Posts.thread
}
