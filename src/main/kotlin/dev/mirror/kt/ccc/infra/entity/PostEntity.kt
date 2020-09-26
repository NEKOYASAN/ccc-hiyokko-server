package dev.mirror.kt.ccc.infra.entity

import dev.mirror.kt.ccc.infra.table.Posts
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class PostEntity(id: EntityID<UUID>): UUIDEntity(id) {
    companion object: UUIDEntityClass<PostEntity>(Posts)

    var thread by ThreadEntity referencedOn Posts.thread
    var author by Posts.author
    var content by Posts.content
}
