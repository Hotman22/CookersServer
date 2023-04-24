package cookers.com.authentication.domain.route

import cookers.com.authentication.domain.repository.AuthenticationRepository
import cookers.com.authentication.domain.util.JwtConfig
import cookers.com.utils.SimpleResponse
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteUser(
    repository: AuthenticationRepository
) {
    route("/authentication/deleteuser") {
        delete {
            val jwtUser = call.authentication.principal as JwtConfig.JwtUser
            if (repository.deleteUser(jwtUser.userMail)) {
                call.respond(OK, SimpleResponse(true, "Successfully delete account!"))
            } else {
                call.respond(BadRequest, SimpleResponse(false, "An unknown error occured"))
            }

        }
    }
}