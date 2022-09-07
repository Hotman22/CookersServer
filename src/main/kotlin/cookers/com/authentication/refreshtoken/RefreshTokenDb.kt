package cookers.com.authentication.refreshtoken

import cookers.com.authentication.User
import cookers.com.utils.database
import org.litote.kmongo.eq

private val users = database.getCollection<User>()

suspend fun getUserById(id: String) = users.findOne(User::id eq id)

var refreshTokenSession: RefreshTokenSession = RefreshTokenSession()

data class RefreshTokenSession(val userId: String = "", val refreshToken: String = "")