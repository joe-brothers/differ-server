package com.joebrothers.differ.server.plugins

import com.joebrothers.differ.server.domain.user.UserService
import com.joebrothers.differ.server.web.userRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userService: UserService by inject()

    routing {
        userRoutes(userService)
    }
}
