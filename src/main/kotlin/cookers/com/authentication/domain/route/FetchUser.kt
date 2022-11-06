package cookers.com.authentication.domain.route

import cookers.com.authentication.domain.repository.AuthenticationRepository
import cookers.com.authentication.domain.util.JwtConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.fetchUser(
    repository: AuthenticationRepository
) {
    authenticate {
        get("/authentication/fetchuser") {
            val jwtUser = call.authentication.principal as JwtConfig.JwtUser
            val user = repository.getUser(jwtUser.userMail)
            user?.let { use ->
                call.respond(use)
            } ?: run {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}