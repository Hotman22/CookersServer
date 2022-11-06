package cookers.com.recipe.domain.route

import cookers.com.authentication.domain.util.JwtConfig
import cookers.com.recipe.createrecipe.registerRecipe
import cookers.com.recipe.domain.model.Recipe
import cookers.com.utils.SimpleResponse
import cookers.com.utils.save
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
                call.respond(BadRequest)
                return@post
            }

            multipartData?.forEachPart { part ->
                if (part is PartData.FileItem) {
                    fileName = part.originalFileName as String
                    part.save(fileName, "uploads/recipe/")
                }
            }
            with(parameters) {
                if (get("title").isNullOrBlank() || getAll("steps").isNullOrEmpty() || getAll("ingredients").isNullOrEmpty()) {
                    call.respond(NotAcceptable, SimpleResponse(false, "The recipe is incomplete"))
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
        return Recipe(title, steps, ingredients, advice, filePath, user.userId, user.userMail)
    }
}