package com.joebrothers.differ.server.domain.user

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.r2dbc.insertAndGetId
import org.jetbrains.exposed.v1.r2dbc.select
import org.jetbrains.exposed.v1.r2dbc.selectAll
import java.util.UUID
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid

interface UserRepository {
    suspend fun existsByUsername(username: String): Boolean
    suspend fun create(username: String, password: String, email: String?): UUID
    suspend fun selectByUsername(username: String): UserEntity?
}

class UserRepositoryImpl : UserRepository {
    override suspend fun existsByUsername(username: String): Boolean =
        UsersTable
            .select(UsersTable.id)
            .where { UsersTable.name eq username }
            .limit(1)
            .count() > 0

    override suspend fun create(username: String, password: String, email: String?): UUID {
        return UsersTable.insertAndGetId {
            it[UsersTable.id] = Uuid.generateV7().toJavaUuid()
            it[UsersTable.name] = username
            it[UsersTable.password] = password
            it[UsersTable.email] = email
        }.value
    }

    override suspend fun selectByUsername(username: String): UserEntity? {
        return UsersTable
            .selectAll()
            .where { UsersTable.name eq username }
            .limit(1)
            .map { UserEntity.fromRow(it) }
            .firstOrNull()
    }
}
