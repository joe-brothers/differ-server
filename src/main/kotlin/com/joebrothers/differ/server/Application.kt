package com.joebrothers.differ.server

import com.joebrothers.differ.server.plugins.configureDatabase
import com.joebrothers.differ.server.plugins.configureKoin
import com.joebrothers.differ.server.plugins.configureRouting
import com.joebrothers.differ.server.plugins.configureSockets
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.netty.EngineMain
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.compression.deflate
import io.ktor.server.plugins.compression.gzip
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

fun main(args: Array<String>) {
    EngineMain.main(args)
}

suspend fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    install(Compression) {
        gzip()
        deflate()
        // TODO: zstd (https://github.com/ktorio/ktor/pull/5031)
    }

    configureDatabase()
    configureKoin()
    configureSockets()
    configureRouting()
}
