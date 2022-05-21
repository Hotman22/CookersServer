package cookers.com.authentication.createuser

import cookers.com.authentication.User
import cookers.com.utils.database
import org.litote.kmongo.eq
import org.litote.kmongo.or

private val users = database.getCollection<User>()

suspend fun registerUser(user: User): Boolean = users.insertOne(user).wasAcknowledged()

suspend fun checkIfUserExists(email: String, userName: String): Boolean = users.findOne(or(User::email eq email, User::userName eq userName)) != null

