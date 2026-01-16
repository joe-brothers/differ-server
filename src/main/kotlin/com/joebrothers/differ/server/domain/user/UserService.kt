package com.joebrothers.differ.server.domain.user

import com.joebrothers.differ.server.interfaces.user.UserDto
import com.joebrothers.differ.server.utils.HashingService
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import java.util.UUID

class UserService(
    private val userRepository: UserRepository,
    private val hashingService: HashingService,
) {
    suspend fun existsByUsername(username: String): Boolean = suspendTransaction {
        userRepository.existsByUsername(username)
    }

    suspend fun signUp(username: String, password: String, email: String?): UUID {
        val hash = hashingService.hash(password)
        return suspendTransaction {
            userRepository.create(
                username = username,
                password = hash,
                email = email,
            )
        }
    }

    suspend fun findByUsername(username: String): UserDto? = suspendTransaction {
        userRepository.selectByUsername(username)
            ?.toUserDto()
    }
}
