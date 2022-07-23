package top.cubik65536.plugins

import io.ktor.server.application.*
import top.cubik65536.routes.web.registerWebRoutes

fun Application.configureRouting() {
    registerWebRoutes()
}
