package cookers.com.recipe.domain.route

import cookers.com.recipe.domain.repository.RecipeRepository
import cookers.com.utils.SimpleResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteRecipe(
    recipeRepository: RecipeRepository,
) {
    authenticate {
        post("/recipe/fetchrecipesubscriptions") {
            val recipeId = call.request.queryParameters["id"]
            recipeId?.let { id ->
                if (recipeRepository.deleteRecipe(recipeId)) {
                    call.respond(HttpStatusCode.OK, SimpleResponse(true, "Successfully delete recipe!"))
                } else {
                    call.respond(HttpStatusCode.Conflict, SimpleResponse(false, "No recipe with this id found"))
                }
            } ?: run {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}