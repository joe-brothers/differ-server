package com.joebrothers.differ.server.domain.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.joebrothers.differ.server.config.TokenConfig
import com.joebrothers.differ.server.domain.user.UserRepository
import com.joebrothers.differ.server.utils.HashingService
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import java.time.Duration
import java.time.Instant

class AuthService(
    private val tokenConfig: TokenConfig,

    private val userRepository: UserRepository,
    private val hashingService: HashingService,
) {

    companion object {
        private val TOKEN_EXPIRED_AFTER = Duration.ofHours(8)
    }

    /** token 반환 */
    suspend fun signIn(username: String, password: String): String? {
        val user = suspendTransaction { userRepository.selectByUsername(username) }
            ?: return null
        if (!hashingService.verify(password, user.password))
            return null

        return JWT.create()
            .withClaim("user.id", user.id.toString())
            .withExpiresAt(Instant.now().plus(TOKEN_EXPIRED_AFTER))
            .sign(Algorithm.HMAC256(tokenConfig.secret))
    }
}
