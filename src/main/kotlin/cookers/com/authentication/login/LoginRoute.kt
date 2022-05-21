package cookers.com.authentication.login

import cookers.com.authentication.JwtConfig
import cookers.com.plugins.jwtConfig
import cookers.com.utils.SimpleResponse
import io.ktor.application.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.loginRoute() {
    route("/authentication/login") {
        post {
            val request = try {
                call.receive<LoginRequest>()
            } catch(e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val email = request.email.lowercase()
            val isPasswordCorrect = checkPasswordForEmail(email, request.password)
            val user = getUser(email)
            if(isPasswordCorrect && user != null) {
                val token = jwtConfig.generateToken(JwtConfig.JwtUser(user.id, user.email.lowercase()))
                val loginResponse = LoginResponse(token)
                call.respond(loginResponse)
            } else {
                call.respond(Unauthorized)
            }
        }
    }
}

