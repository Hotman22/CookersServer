package cookers.com.authentication.refreshtoken

import cookers.com.authentication.JwtConfig
import cookers.com.authentication.Token
import cookers.com.authentication.jwtConfig
import cookers.com.authentication.login.getUserById
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.refreshTokenRoute() {
    route("/authentication/refreshtoken") {
        post {
            val request = try {
                call.receive<RefreshTokenRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val token = findToken(request.refreshToken)
            token?.let { tok ->
                val user = getUserById(tok.userId)
                if (user != null) {
                    val accessToken = jwtConfig.generateToken(JwtConfig.JwtUser(user.id, user.email))
                    val refreshTokenResponse = Token(user.id, accessToken, tok.refreshToken)
                    call.respond(refreshTokenResponse)
                } else {
                    call.respond(HttpStatusCode.Unauthorized)
                }
            } ?: kotlin.run {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}