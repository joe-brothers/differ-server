package com.joebrothers.differ.server.utils

import de.mkammerer.argon2.Argon2Factory

interface HashingService {
    fun hash(password: String): String
    fun verify(password: String, hash: String): Boolean
}

class Argon2HashingService : HashingService {
    private val argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id)

    companion object {
        private const val ITERATION = 10
        /** in kilobytes */
        private const val MEMORY = 65536
        private const val PARALLELISM = 1
    }

    override fun hash(password: String): String =
        argon2.hash(ITERATION, MEMORY, PARALLELISM, password.toCharArray())

    override fun verify(password: String, hash: String): Boolean =
        argon2.verify(hash, password.toCharArray())
}
