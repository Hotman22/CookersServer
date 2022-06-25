package cookers.com

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import cookers.com.authentication.configureRouting

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureRouting()
    }.start(wait = true)
}