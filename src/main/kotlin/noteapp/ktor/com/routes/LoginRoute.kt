package noteapp.ktor.com.routes

import io.ktor.routing.*
import io.ktor.application.call
import io.ktor.auth.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import noteapp.ktor.com.authentication.JwtConfig
import noteapp.ktor.com.data.checkPasswordForEmail
import noteapp.ktor.com.data.getUser
import noteapp.ktor.com.data.requests.AccountRequest
import noteapp.ktor.com.data.responses.SimpleResponse
import noteapp.ktor.com.plugins.jwtConfig

fun Route.loginRoute() {
    route("/login") {
        post {
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
                call.respond(OK, SimpleResponse(false, "The E-Mail or password is incorrect"))
            }
        }
    }

    authenticate {
        get ("/me"){
            val user = call.authentication.principal as JwtConfig.JwtUser
            call.respond(user)
        }
    }
}

