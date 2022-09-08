package cookers.com.authentication.login

import cookers.com.authentication.User
import cookers.com.utils.checkHashForPassword
import cookers.com.utils.database
import org.litote.kmongo.eq

private val users = database.getCollection<User>()

suspend fun  checkPasswordForEmail(email: String, passwordToCheck: String): Boolean {
    val actualPassword = users.findOne(User::email eq email)?.password ?: return false
    return checkHashForPassword(passwordToCheck, actualPassword)
}
suspend fun getUser(email: String) = users.findOne(User::email eq email)

suspend fun getUserById(id: String) = users.findOne(User::id eq id)

