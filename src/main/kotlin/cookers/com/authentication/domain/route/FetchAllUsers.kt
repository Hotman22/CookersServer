package cookers.com.authentication.domain.route

import cookers.com.authentication.domain.repository.AuthenticationRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.fetchAllUsers(
    repository: AuthenticationRepository
) {
    authenticate {
        get("/authentication/fetchusers") {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            val limit = call.request.queryParameters["limit"]?.toInt() ?: 10
            val users = repository.getAllUsers().skip(skip = (page - 1) * limit).limit(limit = limit)
                .partial(true).toList()
            call.respond(users)
        }
    }
}