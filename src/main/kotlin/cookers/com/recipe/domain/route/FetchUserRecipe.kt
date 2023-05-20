package cookers.com.recipe.domain.route

import cookers.com.recipe.domain.model.Recipes
import cookers.com.recipe.domain.repository.RecipeRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.math.ceil

fun Route.fetchUserRecipe(
    repository: RecipeRepository
) {
    authenticate {
        get("/recipe/fetchUserRecipe") {
            val userId = call.request.queryParameters["id"]
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            val size = call.request.queryParameters["size"]?.toInt() ?: 10
            userId?.let { id ->
                val allRecipes = repository.getRecipesByUserId(id)
                val totalPage = ceil(allRecipes.toList().size.toDouble() / size.toDouble()).toInt()
                val recipes = allRecipes.skip(skip = (page - 1) * size).limit(limit = size)
                    .partial(true).toList()
                call.respond(Recipes(totalPage, recipes))
            } ?: run {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}