package com.joebrothers.differ.server.utils

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

inline fun <reified Request : Any, reified Response : Any> Route.typedPost(
    path: String,
    crossinline handler: suspend (Request) -> Response
) {
    post(path) {
        try {
            val requestBody = call.receive<Request>()
            val responseBody = handler(requestBody)

            call.respond(HttpStatusCode.OK, responseBody)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Invalid Request: ${e.message}")
        }
    }
}
