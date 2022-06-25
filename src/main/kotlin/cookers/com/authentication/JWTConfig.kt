package cookers.com.authentication

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Payload
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import java.util.*

class JwtConfig(jwtSecret: String) {

    companion object Constants {
        // jwt config
        private const val jwtIssuer = "com.maaxgr"
        private const val jwtRealm = "com.maaxgr.todolist"

        // claims
        private const val CLAIM_USERID = "userId"
        private const val CLAIM_USERNAME = "userName"

        private const val TOKEN_VALIDITY_MS = 3000000
    }

    private val jwtAlgorithm = Algorithm.HMAC512(jwtSecret)
    private val jwtVerifier: JWTVerifier = JWT
        .require(jwtAlgorithm)
        .withIssuer(jwtIssuer)
        .build()

    /**
     * Generate a token for a authenticated user
     */
    fun generateToken(user: JwtUser): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(jwtIssuer)
        .withClaim(CLAIM_USERID, user.userId)
        .withClaim(CLAIM_USERNAME, user.userName)
        .withExpiresAt(obtainExpirationDate())
        .sign(jwtAlgorithm)

    private fun obtainExpirationDate() = Date(System.currentTimeMillis() + TOKEN_VALIDITY_MS)

    /**
     * Configure the jwt ktor authentication feature
     */
    fun configureKtorFeature(config: JWTAuthenticationProvider.Config) = with(config) {
        verifier(jwtVerifier)
        realm = jwtRealm
        validate {
            val userId = it.payload.getClaim(CLAIM_USERID).asString()
            val userName = it.payload.getClaim(CLAIM_USERNAME).asString()

            if (userId != null && userName != null) {
                JwtUser(userId, userName)
            } else {
                null
            }
        }
    }

    /**
     * POKO, that contains information of an authenticated user that is authenticated via jwt
     */
    data class JwtUser(val userId: String, val userName: String): Principal
}
