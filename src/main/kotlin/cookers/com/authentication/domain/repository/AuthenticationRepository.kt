package cookers.com.authentication.domain.repository

import cookers.com.authentication.domain.model.Token
import cookers.com.authentication.domain.model.User
import org.litote.kmongo.coroutine.CoroutineFindPublisher

interface AuthenticationRepository {
    suspend fun checkIfUserExists(email: String, userName: String): Boolean

    suspend fun checkPasswordForEmail(email: String, passwordToCheck: String): Boolean

    suspend fun registerUser(user: User): Boolean

    suspend fun getUser(email: String): User?

    suspend fun deleteUser(email: String): Boolean

    suspend fun getUserById(id: String): User?

    suspend fun registerRefreshToken(token: Token): Boolean

    suspend fun findToken(refreshToken: String): Token?

    suspend fun saveUserPicture(userId: String, picturePath: String): Boolean

    suspend fun getAllUsers(userId: String): CoroutineFindPublisher<User>

    suspend fun subscribeToUser(currentUser: User, userIdToSubscribe: String): Boolean

    suspend fun unsubscribeToUser(currentUser: User, userIdToUnSubscribe: String): Boolean

    suspend fun getUserSubscribers(currentUserId: String): List<String>

    suspend fun addRecipeFavorite(currentUser: User, recipeIdToAdd: String): Boolean

    suspend fun removeRecipeFavorite(currentUser: User, recipeIdToAdd: String): Boolean

    suspend fun getUsersSubscriptions(userSubscriptions: List<String>): CoroutineFindPublisher<User>
}