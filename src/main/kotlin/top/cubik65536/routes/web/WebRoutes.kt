package top.cubik65536.routes.web

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.registerWebRoutes() {
    routing {
        index()
    }
}
