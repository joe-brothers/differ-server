package com.joebrothers.differ.server.web

import com.joebrothers.differ.server.domain.user.UserService
import com.joebrothers.differ.server.utils.typedPost
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Route.userRoutes(userService: UserService) {
    route("/api") {
        get("/users/{username}/exists") {
            val username = call.parameters["username"]
                ?: throw IllegalArgumentException("Username not found")
            userService.existsByUsername(username)
        }

        typedPost<Int, Int>("/users/authenticate") {
            TODO()
        }
    }
}
