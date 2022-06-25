package cookers.com.authentication.fetchuser

import cookers.com.authentication.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.fetchUser() {
    authenticate {
        get ("/authentication/fetchuser"){
            val user = call.authentication.principal as JwtConfig.JwtUser
            call.respond(user)
        }
    }
}