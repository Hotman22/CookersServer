package cookers.com.authentication.createuser

import cookers.com.authentication.User
import cookers.com.utils.SimpleResponse
import cookers.com.utils.getHashWithSalt
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createUser() {
    route("/authentication/register") {
        post {
            val request = try {
                call.receive<CreateUserRequest>()
            } catch(e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            }
            val email = request.email.lowercase()
            val userExists = checkIfUserExists(email, request.userName)
            if(!userExists) {
                if(registerUser(User(request.userName, request.name, email, getHashWithSalt(request.password)))) {
                    call.respond(OK, SimpleResponse(true, "Successfully created account!"))
                } else {
                    call.respond(BadRequest, SimpleResponse(false, "An unknown error occured"))
                }
            } else {
                call.respond(Conflict, SimpleResponse(false, "A user with that E-Mail already exists"))
            }
        }
    }
}