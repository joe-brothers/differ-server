package com.joebrothers.differ.server.domain.user

import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.select

interface UserRepository {
    suspend fun existsByUsername(username: String): Boolean
}

class UserRepositoryImpl : UserRepository {
    override suspend fun existsByUsername(username: String): Boolean =
        UsersTable
            .select(UsersTable.id)
            .where { UsersTable.name eq username }
            .limit(1)
            .count() > 0
}
