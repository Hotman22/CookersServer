package noteapp.ktor.com.plugins

import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.response.*
import io.ktor.features.*
import io.ktor.gson.*
import noteapp.ktor.com.authentication.JwtConfig
import noteapp.ktor.com.routes.loginRoute
import noteapp.ktor.com.routes.noteRoutes
import noteapp.ktor.com.routes.registerRoute

val jwtConfig = JwtConfig(System.getenv("KTOR_TODOLIST_JWT_SECRET") ?: "default_value")

fun Application.configureRouting() {
    configureModule()

    // Starting point for a Ktor app:
    routing {
        get("/") {
            call.respondText("Hello World 2!")
        }
        get("/welcome") {
            call.respondText("Welcome Home!")
        }
        get("/welcomeBis") {
            call.respondText("Welcome Home Bis!")
        }
    }
    routing {
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
        registerRoute()
        loginRoute()
        noteRoutes()
    }
}
