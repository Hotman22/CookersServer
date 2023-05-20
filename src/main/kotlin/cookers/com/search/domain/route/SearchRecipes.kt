package cookers.com.search.domain.route

import cookers.com.authentication.domain.model.Users
import cookers.com.authentication.domain.util.JwtConfig
import cookers.com.recipe.domain.model.Recipes
import cookers.com.search.domain.model.request.SearchRequest
import cookers.com.search.domain.repository.SearchRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.math.ceil

fun Route.searchRecipes(
    searchRepository: SearchRepository,
) {
    authenticate {
        route("/search/searchrecipes") {
            post {
                val request = try {
                    call.receive<SearchRequest>()
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
                val userJwt = call.authentication.principal as JwtConfig.JwtUser
                val query = request.searchQuery.lowercase()
                val page = call.request.queryParameters["page"]?.toInt() ?: 1
                val size = call.request.queryParameters["size"]?.toInt() ?: 10
                val allRecipes = searchRepository.findRecipes(query, userJwt.userId)
                val totalPage = ceil(allRecipes.toList().size.toDouble() / size.toDouble()).toInt()
                val recipes = allRecipes.skip(skip = (page - 1) * size).limit(limit = size)
                    .partial(true).toList()
                call.respond(Recipes(totalPage, recipes))
            }
        }
    }
}