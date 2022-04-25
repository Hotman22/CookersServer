package cookers.com.plugins

import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.response.*
import io.ktor.features.*
import io.ktor.gson.*
import cookers.com.authentication.JwtConfig
import cookers.com.authentication.fetchuser.fetchUser
import cookers.com.authentication.login.loginRoute
import cookers.com.noteRoutes
import cookers.com.authentication.createuser.createUser

val jwtConfig = JwtConfig(System.getenv("KTOR_TODOLIST_JWT_SECRET") ?: "default_value")

fun Application.configureRouting() {
    configureModule()

    // Starting point for a Ktor app:
    routing {
        get("/") {
            call.respondText("Hello World World!")
        }
    }
}

private fun Application.configureModule() {
    install(Authentication) {
        jwt {
            jwtConfig.configureKtorFeature(this)
        }
    }
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        createUser()
        loginRoute()
        fetchUser()
        noteRoutes()
    }
}
