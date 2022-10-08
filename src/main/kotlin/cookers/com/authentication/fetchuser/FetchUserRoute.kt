package cookers.com.authentication.fetchuser

import cookers.com.authentication.JwtConfig
import cookers.com.authentication.login.getUser
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.fetchUser() {
    authenticate {
        get ("/authentication/fetchuser"){
            val jwtUser = call.authentication.principal as JwtConfig.JwtUser
            val user = getUser(jwtUser.userMail)
            user?.let { use ->
                call.respond(use)
            } ?: run {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}