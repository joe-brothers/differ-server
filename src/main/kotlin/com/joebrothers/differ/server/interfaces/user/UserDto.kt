package com.joebrothers.differ.server.interfaces.user

import com.joebrothers.differ.server.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,

    val username: String,
    val email: String? = null,
)

@Serializable
class Signup {

    @Serializable
    data class Request(
        val username: String,
        val password: String,
        val email: String? = null,
    )

    @Serializable
    data class Response(

        @Serializable(with = UUIDSerializer::class)
        val id: UUID,
    )
}
