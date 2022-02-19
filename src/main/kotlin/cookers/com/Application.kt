package cookers.com

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import cookers.com.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureRouting()
    }.start(wait = true)
}