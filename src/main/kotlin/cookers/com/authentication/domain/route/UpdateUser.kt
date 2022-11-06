package cookers.com.authentication.domain.route

import cookers.com.authentication.domain.repository.AuthenticationRepository
import cookers.com.authentication.domain.util.JwtConfig
import cookers.com.utils.SimpleResponse
import cookers.com.utils.save
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateUser(
    repository: AuthenticationRepository
) {
    authenticate {
        post("/authentication/updateUser") {
            var fileName = ""
            var multipartData: MultiPartData? = null
            val user = call.authentication.principal as JwtConfig.JwtUser

            kotlin.runCatching {
                multipartData = call.receiveMultipart()
            }.getOrElse {
                call.respond(BadRequest)
                return@post
            }

            multipartData?.forEachPart { part ->
                if (part is PartData.FileItem) {
                    fileName = part.originalFileName as String
                    part.save(fileName, "./uploads/user/")
                }
            }

            if (repository.saveUserPicture(user.userId, fileName)) {
                call.respond(OK, SimpleResponse(true, "Successfully save picture!"))
            } else {
                call.respond(BadRequest, SimpleResponse(false, "An unknown error occured"))
            }
        }
    }
}