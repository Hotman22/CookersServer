package cookers.com.authentication.domain.route

import cookers.com.authentication.domain.model.Users
import cookers.com.authentication.domain.repository.AuthenticationRepository
import cookers.com.authentication.domain.util.JwtConfig
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.math.ceil

fun Route.fetchUserSubscriptions(
    authRepository: AuthenticationRepository,
) {
    authenticate {
        get("/authentication/fetchusersubscriptions") {
            val userJwt = call.authentication.principal as JwtConfig.JwtUser
            val currentUser = authRepository.getUserById(userJwt.userId)
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            val size = call.request.queryParameters["size"]?.toInt() ?: 10
            currentUser?.let {
                val allUsers = authRepository.getUsersSubscriptions(currentUser.subscriptions)
                val totalPage = ceil(allUsers.toList().lastIndex.toDouble() / size.toDouble()).toInt()
                val users = allUsers.skip(skip = (page - 1) * size).limit(limit = size)
                    .partial(true).toList()
                call.respond(Users(totalPage, users))
            } ?: run {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}