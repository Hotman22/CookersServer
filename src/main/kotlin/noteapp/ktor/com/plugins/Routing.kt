package noteapp.ktor.com.plugins

import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.response.*
import io.ktor.features.*
import io.ktor.features.ContentTransformationException
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import noteapp.ktor.com.authentication.JwtConfig
import noteapp.ktor.com.data.checkPasswordForEmail
import noteapp.ktor.com.data.getUser
import noteapp.ktor.com.data.requests.AccountRequest
import noteapp.ktor.com.data.responses.SimpleResponse
import noteapp.ktor.com.routes.loginRoute
import noteapp.ktor.com.routes.noteRoutes
import noteapp.ktor.com.routes.registerRoute

val jwtConfig = JwtConfig(System.getenv("KTOR_TODOLIST_JWT_SECRET") ?: "default_value")

fun Application.configureRouting() {
    configureModule()

    // Starting point for a Ktor app:
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
    routing {
            post("/login") {
                val request = try {
                    call.receive<AccountRequest>()
                } catch(e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
                val isPasswordCorrect = checkPasswordForEmail(request.email, request.password)
                val user = getUser(request.email)
                if(isPasswordCorrect && user != null) {
                    val token = jwtConfig.generateToken(JwtConfig.JwtUser(user.id, user.email))
                    call.respond(token)
                } else {
                    call.respond(HttpStatusCode.OK, SimpleResponse(false, "The E-Mail or password is incorrect"))
                }
            }

        authenticate {
            get ("/me"){
                val user = call.authentication.principal as JwtConfig.JwtUser
                call.respond(user)
            }
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
        registerRoute()
        noteRoutes()
    }
}
