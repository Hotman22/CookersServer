package cookers.com

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import cookers.com.data.collections.User
import cookers.com.data.registerUser
import cookers.com.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureRouting()
    }.start(wait = true)
}