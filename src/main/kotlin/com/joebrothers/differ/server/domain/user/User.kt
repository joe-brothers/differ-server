package com.joebrothers.differ.server.domain.user

import com.joebrothers.differ.server.interfaces.user.UserDto
import io.mcarle.konvert.api.KonvertTo
import io.mcarle.konvert.api.Mapping
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.UUIDTable
import org.jetbrains.exposed.v1.dao.UUIDEntity
import org.jetbrains.exposed.v1.dao.UUIDEntityClass
import java.util.UUID

object UsersTable : UUIDTable("users") {
    val name = varchar("name", length = 50)
    val password = varchar("password", length = 128)
    val email = varchar("email", length = 254).nullable()
}

@KonvertTo(
    UserDto::class,
    mappings = [
        Mapping(source = "id", target = "id", ),
        Mapping(source = "name", target = "username"),
    ],
)
class UserEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserEntity>(UsersTable)

    var name by UsersTable.name
    var password by UsersTable.password
    var email by UsersTable.email
}
