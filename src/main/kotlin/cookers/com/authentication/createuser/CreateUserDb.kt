package cookers.com.authentication.createuser

import cookers.com.authentication.User
import cookers.com.utils.database
import org.litote.kmongo.eq

private val users = database.getCollection<User>()

suspend fun registerUser(user: User): Boolean = users.insertOne(user).wasAcknowledged()

suspend fun checkIfUserExists(email: String): Boolean = users.findOne(User::email eq email) != null

