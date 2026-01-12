package com.joebrothers.differ.server.web

import com.joebrothers.differ.server.domain.auth.AuthService
import com.joebrothers.differ.server.domain.user.UserService
import com.joebrothers.differ.server.interfaces.user.SignIn
import com.joebrothers.differ.server.interfaces.user.Signup
import com.joebrothers.differ.server.utils.typedPost
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.plugins.ratelimit.RateLimitName
import io.ktor.server.plugins.ratelimit.rateLimit
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import org.koin.ktor.ext.inject
import java.time.Instant
import java.util.Date

fun Route.userRoutes() {
    val authService: AuthService by inject()
    val userService: UserService by inject()

    route("/api") {
        get {
            call.respond("hi")
        }

        rateLimit(RateLimitName("users/exists")) {
            get("/users/{username}/exists") {
                val username = call.parameters["username"]
                    ?: throw IllegalArgumentException("Username not found")
                call.respond(HttpStatusCode.OK, userService.existsByUsername(username))
            }
        }

        rateLimit(RateLimitName("users/sign-up")) {
            typedPost<Signup.Request, Signup.Response>("/users/sign-up") { request ->
                val id = userService.signUp(
                    username = request.username,
                    password = request.password,
                    email = request.email,
                )

                Signup.Response(id)
            }
        }

        typedPost<SignIn.Request, SignIn.Response>("/users/authenticate") { request ->
            val token = authService.signIn(request.username, request.password)
                ?: throw IllegalArgumentException("Failed to sign in")

            SignIn.Response(token)
        }

        authenticate("auth-jwt") {
            get("/users/me") {
                val principal = call.principal<JWTPrincipal>()
                    ?: throw IllegalArgumentException("No JWT Principal")
                val expiresAt = principal.expiresAt
                    ?: throw IllegalArgumentException("Expiration time not found")
                require(expiresAt <= Date.from(Instant.now()))

                val username = principal.payload.getClaim("user.id").asString()

                val user = suspendTransaction { userService.findByUsername(username) }
                    ?: throw IllegalArgumentException("User not found")
                call.respond(HttpStatusCode.OK, user)
            }
        }
    }
}
