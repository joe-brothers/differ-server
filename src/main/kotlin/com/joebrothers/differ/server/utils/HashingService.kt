package com.joebrothers.differ.server.utils

import de.mkammerer.argon2.Argon2Factory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface HashingService {
    suspend fun hash(password: String): String
    suspend fun verify(password: String, hash: String): Boolean
}

class Argon2HashingService : HashingService {
    private val argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id)

    companion object {
        private const val ITERATION = 10

        /** in kilobytes */
        private const val MEMORY = 65536
        private const val PARALLELISM = 1
    }

    override suspend fun hash(password: String): String = withContext(Dispatchers.Default) {
        return@withContext argon2.hash(ITERATION, MEMORY, PARALLELISM, password.toCharArray())
    }

    override suspend fun verify(password: String, hash: String): Boolean = withContext(Dispatchers.Default) {
        return@withContext argon2.verify(hash, password.toCharArray())
    }
}
