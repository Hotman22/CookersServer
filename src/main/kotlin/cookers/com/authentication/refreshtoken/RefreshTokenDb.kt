package cookers.com.authentication.refreshtoken

import cookers.com.authentication.Token
import cookers.com.authentication.User
import cookers.com.utils.database
import org.litote.kmongo.eq

private val tokenDb = database.getCollection<Token>()

suspend fun registerRefreshToken(token: Token) = tokenDb.insertOne(token).wasAcknowledged()

suspend fun findToken(refreshToken: String) = tokenDb.findOne(Token::refreshToken eq refreshToken)