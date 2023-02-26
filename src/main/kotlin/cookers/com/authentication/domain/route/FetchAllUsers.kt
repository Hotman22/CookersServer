package cookers.com.authentication.domain.route

import cookers.com.authentication.domain.model.Users
import cookers.com.authentication.domain.repository.AuthenticationRepository
import cookers.com.authentication.domain.util.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.math.ceil

fun Route.fetchAllUsers(
    repository: AuthenticationRepository
) {
    authenticate {
        get("/authentication/fetchusers") {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            val size = call.request.queryParameters["size"]?.toInt() ?: 10
            val userJwt = call.authentication.principal as JwtConfig.JwtUser
            val allUsers  = repository.getAllUsers(userJwt.userId)
            val totalPage = ceil(allUsers.toList().lastIndex.toDouble() / size.toDouble()).toInt()
            val users = allUsers.skip(skip = (page - 1) * size).limit(limit = size)
                .partial(true).toList()
            call.respond(Users(totalPage, users))
        }
    }
}