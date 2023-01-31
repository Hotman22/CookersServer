package cookers.com.authentication.data.data_source

import cookers.com.authentication.domain.model.User
import cookers.com.utils.checkHashForPassword
import cookers.com.utils.database
import org.litote.kmongo.eq
import org.litote.kmongo.or
import org.litote.kmongo.setValue

class AuthenticationDb {
    private val users = database.getCollection<User>()

    suspend fun registerUser(user: User): Boolean = users.insertOne(user).wasAcknowledged()

    suspend fun checkIfUserExists(email: String, userName: String): Boolean =
        users.findOne(or(User::email eq email, User::userName eq userName)) != null

    suspend fun checkPasswordForEmail(email: String, passwordToCheck: String): Boolean {
        val actualPassword = users.findOne(User::email eq email)?.password ?: return false
        return checkHashForPassword(passwordToCheck, actualPassword)
    }

    suspend fun getUser(email: String) = users.findOne(User::email eq email)

    suspend fun getUserById(id: String) = users.findOne(User::id eq id)

    suspend fun saveUserPicture(userId: String, picturePath: String): Boolean =
        users.updateOne(User::id eq userId, setValue(User::pictureFilePath, picturePath)).wasAcknowledged()

    fun getAllUsers() = users.find()

    suspend fun subscribeToUser(currentUser: User, userIdToSubscribe: String): Boolean {
        if (!currentUser.subscriptions.contains(userIdToSubscribe)) {
            currentUser.subscriptions.add(userIdToSubscribe)
        return users.updateOne(User::id eq currentUser.id, setValue(User::subscriptions, currentUser.subscriptions))
            .wasAcknowledged()}
        return false

    }

    suspend fun unsubscribeToUser(currentUser: User, userIdToSubscribe: String): Boolean {
        if (currentUser.subscriptions.contains(userIdToSubscribe)) {
            currentUser.subscriptions.remove(userIdToSubscribe)
            return users.updateOne(User::id eq currentUser.id, setValue(User::subscriptions, currentUser.subscriptions))
                .wasAcknowledged()}
        return false
    }
}
