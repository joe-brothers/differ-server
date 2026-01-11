package com.joebrothers.differ.server.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.origin
import io.ktor.server.plugins.ratelimit.RateLimit
import io.ktor.server.plugins.ratelimit.RateLimitName
import kotlin.time.Duration.Companion.seconds

fun Application.configureRateLimit() {
    install(RateLimit) {
        register(RateLimitName("users/sign-up")) {
            rateLimiter(limit = 3, refillPeriod = 60.seconds)
            requestKey { call ->
                call.request.origin.remoteAddress
            }
        }

        register(RateLimitName("users/exists")) {
            rateLimiter(limit = 60, refillPeriod = 60.seconds)
            requestKey { call ->
                call.request.origin.remoteAddress
            }
        }
    }
}
