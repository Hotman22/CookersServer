package cookers.com.authentication.fetchuser

import cookers.com.authentication.JwtConfig
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.fetchUser() {
    authenticate {
        get ("/me"){
            val user = call.authentication.principal as JwtConfig.JwtUser
            call.respond(user)
        }
    }
}