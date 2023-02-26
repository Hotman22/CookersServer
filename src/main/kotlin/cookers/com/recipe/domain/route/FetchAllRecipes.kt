package cookers.com.recipe.domain.route

import cookers.com.authentication.domain.util.JwtConfig
import cookers.com.recipe.domain.model.Recipes
import cookers.com.recipe.domain.repository.RecipeRepository
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.math.ceil

fun Route.fetchAllRecipes(
    repository: RecipeRepository
) {
    authenticate {
        get("/recipe/fetchrecipes") {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            val size = call.request.queryParameters["size"]?.toInt() ?: 10
            val userJwt = call.authentication.principal as JwtConfig.JwtUser
            val allRecipes  = repository.getAllRecipes(userJwt.userId)
            val totalPage = ceil(allRecipes.toList().size.toDouble() / size.toDouble()).toInt()
            val recipes = allRecipes.skip(skip = (page - 1) * size).limit(limit = size)
                .partial(true).toList()
            call.respond(OK, Recipes(totalPage, recipes))
        }
    }
}