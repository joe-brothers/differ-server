package com.joebrothers.differ.server.plugins

import com.joebrothers.differ.server.config.TokenConfig
import com.joebrothers.differ.server.di.appModules
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger(org.koin.core.logger.Level.INFO)

        // configs
        val jwtConfig = environment.config.config("jwt").run {
            TokenConfig(
                secret = property("secret").getString(),
            )
        }

        val configModule = module {
            single { jwtConfig }
        }

        modules(appModules + configModule)
    }
}
