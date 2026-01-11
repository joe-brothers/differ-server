package com.joebrothers.differ.server.web

import com.joebrothers.differ.server.domain.user.UserService
import com.joebrothers.differ.server.interfaces.user.Signup
import com.joebrothers.differ.server.utils.typedPost
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.userRoutes(userService: UserService) {
    route("/api") {
        get {
            call.respond("hi")
        }

        get("/users/{username}/exists") {
            val username = call.parameters["username"]
                ?: throw IllegalArgumentException("Username not found")
            call.respond(HttpStatusCode.OK, userService.existsByUsername(username))
        }

        typedPost<Signup.Request, Signup.Response>("/users/sign-up") { request ->
            val id = userService.signUp(
                username = request.username,
                password = request.password,
                email = request.email,
            )

            Signup.Response(id)
        }

        typedPost<Int, Int>("/users/authenticate") {
            TODO()
        }
    }
}
