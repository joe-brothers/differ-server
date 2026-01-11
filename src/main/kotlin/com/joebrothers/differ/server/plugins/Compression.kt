package com.joebrothers.differ.server.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.compression.deflate
import io.ktor.server.plugins.compression.gzip

fun Application.configureCompression() {
    install(Compression) {
        gzip()
        deflate()
        // TODO: zstd (https://github.com/ktorio/ktor/pull/5031)
    }
}
