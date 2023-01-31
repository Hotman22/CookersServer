package cookers.com.authentication.domain.route

import cookers.com.authentication.domain.repository.AuthenticationRepository
import cookers.com.authentication.domain.util.JwtConfig
import cookers.com.utils.SimpleResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.subscribeToUser(
    authRepository: AuthenticationRepository
) {
    authenticate {
        post("/authentication/subscribe") {
            val userId = call.request.queryParameters["id"]
            val userJwt = call.authentication.principal as JwtConfig.JwtUser
            val currentUser = authRepository.getUserById(userJwt.userId)
            if (currentUser != null && userId != null) {
                if (authRepository.subscribeToUser(currentUser, userId)) {
                    call.respond(HttpStatusCode.OK, SimpleResponse(true, "Successfully subscribe to user!"))
                } else {
                    call.respond(HttpStatusCode.Conflict, SimpleResponse(false, "Already subscribe to this user "))
                }
            } else {
                call.respond(HttpStatusCode.NotFound, SimpleResponse(false, "Current user or user id is null"))
            }
        }
    }
}