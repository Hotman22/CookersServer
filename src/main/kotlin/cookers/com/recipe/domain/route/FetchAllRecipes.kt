package cookers.com.recipe.domain.route

import cookers.com.recipe.domain.model.Recipes
import cookers.com.recipe.domain.repository.RecipeRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.math.ceil

fun Route.fetchAllUsers(
    repository: RecipeRepository
) {
    authenticate {
        get("/authentication/fetchrecipes") {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            val size = call.request.queryParameters["size"]?.toInt() ?: 10
            val allRecipes  = repository.getAllRecipes()
            val totalPage = ceil(allRecipes.toList().lastIndex.toDouble() / size.toDouble()).toInt()
            val recipes = allRecipes.skip(skip = (page - 1) * size).limit(limit = size)
                .partial(true).toList()
            call.respond(Recipes(totalPage, recipes))
        }
    }
}