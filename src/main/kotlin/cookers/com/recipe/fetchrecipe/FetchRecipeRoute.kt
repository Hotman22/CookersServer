package cookers.com.recipe.fetchrecipe

import cookers.com.authentication.JwtConfig
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.fetchRecipeRoute() {
    authenticate {
        get("/recipe/fetchrecipe") {
            val email = (call.authentication.principal as JwtConfig.JwtUser).userMail
            val recipes = getRecipesForUser(email)
            call.respond(OK, recipes)
        }
    }
}