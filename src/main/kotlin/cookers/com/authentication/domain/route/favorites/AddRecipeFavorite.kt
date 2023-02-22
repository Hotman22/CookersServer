package cookers.com.authentication.domain.route.favorites

import cookers.com.authentication.domain.repository.AuthenticationRepository
import cookers.com.authentication.domain.util.JwtConfig
import cookers.com.utils.SimpleResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.addRecipeFavorite(
    authRepository: AuthenticationRepository,
) {
    authenticate {
        post("/authentication/addrecipefavorite") {
            val userJwt = call.authentication.principal as JwtConfig.JwtUser
            val currentUser = authRepository.getUserById(userJwt.userId)
            val recipeId = call.request.queryParameters["id"]
            if (currentUser != null && recipeId != null) {
                if (authRepository.addRecipeFavorite(currentUser, recipeId)) {
                    call.respond(HttpStatusCode.OK, SimpleResponse(true, "Successfully add recipe to favorite!"))
                } else {
                    call.respond(HttpStatusCode.Conflict, SimpleResponse(false, "Already have this recipe in favorite"))
                }
            } else {
                call.respond(HttpStatusCode.NotFound, SimpleResponse(false, "Current user or recipe id is null"))
            }
        }
    }
}