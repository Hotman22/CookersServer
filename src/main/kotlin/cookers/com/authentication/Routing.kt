package cookers.com.authentication

import cookers.com.authentication.fetchuser.fetchUser
import cookers.com.authentication.login.loginRoute
import cookers.com.authentication.createuser.createUser
import cookers.com.recipe.createrecipe.createRecipe
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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
    install(DoubleReceive)
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
        //noteRoutes()
        createRecipe()
    }
}
