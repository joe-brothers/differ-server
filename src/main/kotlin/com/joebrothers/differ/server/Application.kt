package com.joebrothers.differ.server

import com.joebrothers.differ.server.plugins.configureAuth
import com.joebrothers.differ.server.plugins.configureCompression
import com.joebrothers.differ.server.plugins.configureContentNegotiation
import com.joebrothers.differ.server.plugins.configureDatabase
import com.joebrothers.differ.server.plugins.configureKoin
import com.joebrothers.differ.server.plugins.configureRateLimit
import com.joebrothers.differ.server.plugins.configureRouting
import com.joebrothers.differ.server.plugins.configureSockets
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

suspend fun Application.module() {
    configureKoin()

    configureAuth()
    configureCompression()
    configureContentNegotiation()
    configureDatabase()
    configureRateLimit()
    configureRouting()
    configureSockets()
}
