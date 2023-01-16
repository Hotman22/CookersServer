package cookers.com.authentication.domain.repository

import cookers.com.authentication.domain.model.Token
import cookers.com.authentication.domain.model.User
import org.litote.kmongo.coroutine.CoroutineFindPublisher

interface AuthenticationRepository {
    suspend fun checkIfUserExists(email: String, userName: String): Boolean

    suspend fun checkPasswordForEmail(email: String, passwordToCheck: String): Boolean

    suspend fun registerUser(user: User): Boolean

    suspend fun getUser(email: String): User?

    suspend fun getUserById(id: String): User?

    suspend fun registerRefreshToken(token: Token): Boolean

    suspend fun findToken(refreshToken: String): Token?

    suspend fun saveUserPicture(userId: String, picturePath: String): Boolean

    suspend fun getAllUsers(): CoroutineFindPublisher<User>
}