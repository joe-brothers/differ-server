package com.joebrothers.differ.server

import com.joebrothers.differ.server.plugins.configureKoin
import com.joebrothers.differ.server.plugins.configureRouting
import com.joebrothers.differ.server.plugins.configureSockets
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
//    configureDatabases()
    configureKoin()
    configureSockets()
    configureRouting()
}
