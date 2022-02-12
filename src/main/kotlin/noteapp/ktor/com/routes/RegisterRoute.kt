package noteapp.ktor.com.routes

import io.ktor.application.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.routing.post
import noteapp.ktor.com.data.responses.SimpleResponse
import noteapp.ktor.com.data.checkIfUserExists
import noteapp.ktor.com.data.collections.User
import noteapp.ktor.com.data.registerUser
import noteapp.ktor.com.data.requests.AccountRequest
import noteapp.ktor.com.security.getHashWithSalt

fun Route.registerRoute() {
    get("/welcomeBis") {
        call.respondText("Welcome Home Bis!")
    }
    get("/welcomeBis2") {
        call.respondText("Welcome Home Bis Test!")
    }
    route("/register") {
        post {
            val request = try {
                call.receive<AccountRequest>()
            } catch(e: ContentTransformationException) {
                call.respond(BadRequest)
                return@post
            }
            val userExists = checkIfUserExists(request.email)
            if(!userExists) {
                if(registerUser(User(request.email, getHashWithSalt(request.password)))) {
                    call.respond(OK, SimpleResponse(true, "Successfully created account!"))
                } else {
                    call.respond(OK, SimpleResponse(false, "An unknown error occured"))
                }
            } else {
                call.respond(OK, SimpleResponse(false, "A user with that E-Mail already exists"))
            }
        }
    }
}