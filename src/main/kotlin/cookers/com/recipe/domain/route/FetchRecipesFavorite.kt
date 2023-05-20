package cookers.com.authentication.domain.route.favorites

import cookers.com.authentication.domain.repository.AuthenticationRepository
import cookers.com.authentication.domain.util.JwtConfig
import cookers.com.recipe.domain.model.Recipes
import cookers.com.recipe.domain.repository.RecipeRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.math.ceil

fun Route.fetchRecipeFavorite(
    authRepository: AuthenticationRepository,
    recipeRepository: RecipeRepository,
) {
    authenticate {
        get("/recipe/fetchrecipefavorite") {
            val userJwt = call.authentication.principal as JwtConfig.JwtUser
            val currentUser = authRepository.getUserById(userJwt.userId)
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            val size = call.request.queryParameters["size"]?.toInt() ?: 10
            currentUser?.let {
                val allRecipes = recipeRepository.getRecipesFavorite(currentUser.recipeFavorites)
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