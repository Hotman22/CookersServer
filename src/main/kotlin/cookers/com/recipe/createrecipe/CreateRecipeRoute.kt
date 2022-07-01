package cookers.com.recipe.createrecipe

import cookers.com.authentication.JwtConfig
import cookers.com.utils.SimpleResponse
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.NotAcceptable
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.createRecipe() {
    authenticate {
        post("/recipe/createrecipe") {
            var fileName = ""
            var multipartData: MultiPartData? = null
            val parameters: Parameters = call.receiveParameters()
            val user = call.authentication.principal as JwtConfig.JwtUser

            kotlin.runCatching {
                multipartData = call.receiveMultipart()
            }.getOrElse {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            multipartData?.forEachPart { part ->
                if (part is PartData.FileItem) {
                    fileName = part.originalFileName as String
                    part.save(fileName)
                }
            }
            with(parameters) {
                if (get("title").isNullOrBlank() || getAll("steps").isNullOrEmpty() || getAll("ingredients").isNullOrEmpty()) {
                    call.respond(
                        NotAcceptable,
                        SimpleResponse(false, "The recipe is incomplete, please fill all the fields")
                    )
                    return@post
                }
                if (registerRecipe(createRecipe(this, fileName, user))) {
                    call.respond(OK, SimpleResponse(true, "Successfully created recipe!"))
                } else {
                    call.respond(BadRequest, SimpleResponse(false, "An unknown error occured"))
                }
            }
        }
    }
}

private fun createRecipe(parameters: Parameters, filePath: String, user: JwtConfig.JwtUser): Recipe {
    with(parameters) {
        val title = get("title") ?: ""
        val steps = getAll("steps") ?: emptyList()
        val ingredients = getAll("ingredients") ?: emptyList()
        val advice = get("advice") ?: ""
        return Recipe(title, steps, ingredients, advice, filePath, user.userId, user.userName)
    }
}

fun PartData.FileItem.save(fileName: String): String {
    val fileBytes = this.streamProvider().readBytes()
    val folder = File("uploads/")
    folder.mkdir()
    File("uploads/$fileName").writeBytes(fileBytes)
    return fileName
}