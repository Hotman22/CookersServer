package cookers.com.authentication.domain.route

import cookers.com.authentication.domain.repository.AuthenticationRepository
import cookers.com.authentication.domain.util.JwtConfig
import cookers.com.recipe.domain.repository.RecipeRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.fetchUser(
    repository: AuthenticationRepository,
    recipeRepo: RecipeRepository,
) {
    authenticate {
        get("/authentication/fetchuser") {
            val jwtUser = call.authentication.principal as JwtConfig.JwtUser
            val user = repository.getUser(jwtUser.userMail)
            user?.let { use ->
                val recipes = recipeRepo.getRecipesByUserId(use.id).toList().size
                val subscriber = repository.getUserSubscribers(use.id)
                use.apply {
                    recipesNumber = recipes
                    subscribers = subscriber.size
                }
                call.respond(use)
            } ?: run {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}