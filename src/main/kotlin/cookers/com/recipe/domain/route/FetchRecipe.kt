package cookers.com.recipe.domain.route

import cookers.com.authentication.domain.util.JwtConfig
import cookers.com.recipe.domain.repository.RecipeRepository
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.fetchRecipe(
    repository: RecipeRepository
) {
    authenticate {
        get("/recipe/fetchrecipe") {
            val email = (call.authentication.principal as JwtConfig.JwtUser).userMail
            val recipes = repository.getRecipesForUser(email)
            call.respond(OK, recipes)
        }
    }
}