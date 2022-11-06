package cookers.com.authentication.data.repository

import cookers.com.authentication.data.data_source.AuthenticationDb
import cookers.com.authentication.data.data_source.RefreshTokenDb
import cookers.com.authentication.domain.model.Token
import cookers.com.authentication.domain.model.User
import cookers.com.authentication.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val authDb: AuthenticationDb,
    private val refreshTokenDb: RefreshTokenDb,
) : AuthenticationRepository {
    override suspend fun checkIfUserExists(email: String, userName: String): Boolean =
        authDb.checkIfUserExists(email, userName)

    override suspend fun checkPasswordForEmail(email: String, passwordToCheck: String): Boolean =
        authDb.checkPasswordForEmail(email, passwordToCheck)

    override suspend fun registerUser(user: User): Boolean = authDb.registerUser(user)

    override suspend fun getUser(email: String) = authDb.getUser(email)

    override suspend fun getUserById(id: String) = authDb.getUserById(id)

    override suspend fun registerRefreshToken(token: Token) = refreshTokenDb.registerRefreshToken(token)

    override suspend fun findToken(refreshToken: String) = refreshTokenDb.findToken(refreshToken)

    override suspend fun saveUserPicture(userId: String, picturePath: String): Boolean =
        authDb.saveUserPicture(userId, picturePath)
}