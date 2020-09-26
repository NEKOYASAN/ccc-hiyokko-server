package dev.mirror.kt.ccc.infra

import dev.mirror.kt.ccc.domain.Post
import dev.mirror.kt.ccc.infra.entity.PostEntity
import dev.mirror.kt.ccc.infra.entity.ThreadEntity
import dev.mirror.kt.ccc.domain.Thread as DomainThread

fun ThreadEntity.toDomain(): DomainThread = DomainThread(
    this.author,
    this.title
)

fun PostEntity.toDomain(): Post = Post(
    this.author,
    this.content
)
