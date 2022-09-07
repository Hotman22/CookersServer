package cookers.com.authentication.refreshtoken

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
            if(oldRefreshToken == refreshTokenSession) {
                val newRefreshToken = jwtConfig.generateRefreshToken()
                val loginResponse = RefreshTokenResponse(newRefreshToken)
                refreshTokenSession = newRefreshToken
                call.respond(loginResponse)
            } else {
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}