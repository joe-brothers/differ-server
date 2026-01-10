package com.joebrothers.differ.server.plugins

import com.joebrothers.differ.server.di.allModules
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger(org.koin.core.logger.Level.INFO)
        modules(allModules)
    }
}
