package cookers.com.recipe.createrecipe

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

fun Route.createRecipe() {
    authenticate {
        var fileDescription = ""
        var fileName = ""

        post("/createrecipe") {

            val multipartData = try {
                call.receiveMultipart()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FormItem -> {
                        fileDescription = part.value
                    }
                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        var fileBytes = part.streamProvider().readBytes()
                        File("uploads/$fileName").writeBytes(fileBytes)
                    }
                }
            }
            call.respondText("$fileDescription is uploaded to 'uploads/$fileName'")
        }
    }
}
