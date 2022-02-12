package noteapp.ktor.com

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import noteapp.ktor.com.data.collections.User
import noteapp.ktor.com.data.registerUser
import noteapp.ktor.com.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = 8000) {
        configureRouting()
    }.start(wait = true)
}