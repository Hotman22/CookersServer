package cookers.com.authentication.domain.route

import cookers.com.authentication.domain.model.User
import cookers.com.authentication.domain.model.request.CreateUserRequest
import cookers.com.authentication.domain.repository.AuthenticationRepository
import cookers.com.utils.SimpleResponse
import cookers.com.utils.getHashWithSalt
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createUser(
    repository: AuthenticationRepository
) {
    route("/authentication/register") {
        post {
            val request = try {
                call.receive<CreateUserRequest>()
            } catch (e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            }
            val email = request.email.lowercase()
            val user = User(
                request.userName,
                request.name,
                email,
                getHashWithSalt(request.password)
            )
            val userExists = repository.checkIfUserExists(email, request.userName)
            if (!userExists) {
                if (repository.registerUser(user)) {
                    call.respond(OK, SimpleResponse(true, "Successfully created account!"))
                } else {
                    call.respond(BadRequest, SimpleResponse(false, "An unknown error occured"))
                }
            } else {
                call.respond(Conflict, SimpleResponse(false, "A user with that E-Mail or UserName already exists"))
            }
        }
    }
}