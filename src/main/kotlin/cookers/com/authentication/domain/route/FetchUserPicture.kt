package cookers.com.authentication.domain.route

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.fetchUserPicture() {
    authenticate {
        get("/authentication/fetchuserpicture") {
            val picturePath = call.request.queryParameters["picturePath"]
            call.respondFile(File("uploads/user/$picturePath"))
        }
    }
}