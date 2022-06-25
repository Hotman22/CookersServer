package cookers.com

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import cookers.com.authentication.configureRouting

fun main() {
    embeddedServer(Netty, port = 8080) {
        configureRouting()
    }.start(wait = true)
}