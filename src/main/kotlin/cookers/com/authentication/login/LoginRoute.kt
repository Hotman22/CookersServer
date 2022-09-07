package cookers.com.authentication.login

import cookers.com.authentication.JwtConfig
import cookers.com.authentication.User
import cookers.com.authentication.jwtConfig
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.loginRoute() {
    authenticate {
        route("/authentication/refreshtoken") {
            post {
                val request = try {
                    call.receive<LoginRequest>()
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
                val email = request.email.lowercase()
                val isPasswordCorrect = checkPasswordForEmail(email, request.password)
                val user = getUser(email)
                if (isPasswordCorrect && user != null) {
                    val token = jwtConfig.generateToken(JwtConfig.JwtUser(user.id, user.email))
                    val refreshToken = jwtConfig.generateRefreshToken()
                    val loginResponse = LoginResponse(token, refreshToken)
                    refreshTokenSession = refreshToken
                    call.respond(loginResponse)
                } else {
                    call.respond(Unauthorized)
                }
            }
        }
    }
}

