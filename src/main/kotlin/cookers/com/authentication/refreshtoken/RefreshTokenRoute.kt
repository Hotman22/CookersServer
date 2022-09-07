package cookers.com.authentication.refreshtoken

import cookers.com.authentication.JwtConfig
import cookers.com.authentication.jwtConfig
import cookers.com.authentication.login.*
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
            } catch(e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val oldRefreshToken = request.refreshToken
            val user = getUserById(refreshTokenSession.userId)
            if(user != null && oldRefreshToken == refreshTokenSession.refreshToken) {
                val accessToken = jwtConfig.generateToken(JwtConfig.JwtUser(user.id, user.email))
                val refreshTokenResponse = RefreshTokenResponse(accessToken, oldRefreshToken)
                call.respond(refreshTokenResponse)
            } else {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}