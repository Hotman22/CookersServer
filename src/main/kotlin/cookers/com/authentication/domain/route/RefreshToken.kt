package cookers.com.authentication.domain.route

import cookers.com.authentication.domain.model.Token
import cookers.com.authentication.domain.model.request.RefreshTokenRequest
import cookers.com.authentication.domain.repository.AuthenticationRepository
import cookers.com.authentication.domain.util.JwtConfig
import cookers.com.jwtConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.refreshTokenRoute(
    repository: AuthenticationRepository
) {
    route("/authentication/refreshtoken") {
        post {
            val request = try {
                call.receive<RefreshTokenRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val token = repository.findToken(request.refreshToken)
            token?.let { tok ->
                val user = repository.getUserById(tok.userId)
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