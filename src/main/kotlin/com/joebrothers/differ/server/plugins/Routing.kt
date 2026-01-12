package com.joebrothers.differ.server.plugins

import com.joebrothers.differ.server.web.userRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        userRoutes()
    }
}
