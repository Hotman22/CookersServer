package cookers.com.authentication.data.data_source

import cookers.com.authentication.domain.model.Token
import cookers.com.utils.database
import org.litote.kmongo.eq

class RefreshTokenDb {
    private val tokenDb = database.getCollection<Token>()

    suspend fun registerRefreshToken(token: Token) = tokenDb.insertOne(token).wasAcknowledged()

    suspend fun findToken(refreshToken: String) = tokenDb.findOne(Token::refreshToken eq refreshToken)
}
