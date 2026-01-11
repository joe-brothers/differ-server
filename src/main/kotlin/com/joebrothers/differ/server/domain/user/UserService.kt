package com.joebrothers.differ.server.domain.user

import com.joebrothers.differ.server.utils.HashingService
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction

interface UserService {
    suspend fun existsByUsername(username: String): Boolean
    suspend fun signUp(username: String, password: String, email: String?): UserEntity
}

class UserServiceImpl(
    private val userRepository: UserRepository,
    private val hashingService: HashingService,
) : UserService {

    override suspend fun existsByUsername(username: String): Boolean {
        return suspendTransaction {
            userRepository.existsByUsername(username)
        }
    }

    override suspend fun signUp(username: String, password: String, email: String?): UserEntity {
        TODO()
    }
}
