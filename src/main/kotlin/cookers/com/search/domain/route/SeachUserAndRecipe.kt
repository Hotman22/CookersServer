package cookers.com.search.domain.route

import cookers.com.authentication.domain.util.JwtConfig
import cookers.com.search.domain.model.SearchUserAndRecipeResult
import cookers.com.search.domain.model.request.SearchRequest
import cookers.com.search.domain.repository.SearchRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.searchResult(
    searchRepository: SearchRepository,
) {
    authenticate {
        route("/search/seachuserandrecipe") {
            post {
                val request = try {
                    call.receive<SearchRequest>()
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
                val userJwt = call.authentication.principal as JwtConfig.JwtUser
                val query = request.searchQuery.lowercase()
                val usersAndRecipes = searchRepository.findUserAndRecipeByQuery(query, userJwt.userId)
                val result = SearchUserAndRecipeResult(usersAndRecipes.first, usersAndRecipes.second)
                call.respond(HttpStatusCode.OK, result)
            }
        }
    }
}