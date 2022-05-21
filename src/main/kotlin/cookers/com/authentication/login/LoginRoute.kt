package cookers.com.authentication.login

import io.ktor.application.call
import io.ktor.features.ContentTransformationException
import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import cookers.com.authentication.JwtConfig
import cookers.com.utils.SimpleResponse
import cookers.com.plugins.jwtConfig
import io.ktor.http.HttpStatusCode.Companion.Unauthorized

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

