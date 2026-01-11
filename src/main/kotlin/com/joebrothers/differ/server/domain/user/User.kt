package com.joebrothers.differ.server.domain.user

import com.joebrothers.differ.server.interfaces.user.UserDto
import io.mcarle.konvert.api.KonvertTo
import io.mcarle.konvert.api.Mapping
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.dao.id.UUIDTable
import java.util.UUID

object UsersTable : UUIDTable("users") {
    val name = varchar("name", length = 50)
        .uniqueIndex()
        .index(customIndexName = "ix_users_name")
    val password = varchar("password", length = 128)
    val email = varchar("email", length = 254).nullable()
        .uniqueIndex()
}

@KonvertTo(
    UserDto::class,
    mappings = [
        Mapping(source = "name", target = "username"),
    ],
)
data class UserEntity(
    val id: UUID,
    val name: String,
    val password: String,
    val email: String?,
) {
    companion object {
        fun fromRow(row: ResultRow) = UserEntity(
            id = row[UsersTable.id].value,
            name = row[UsersTable.name],
            password = row[UsersTable.password],
            email = row[UsersTable.email],
        )
    }
}
