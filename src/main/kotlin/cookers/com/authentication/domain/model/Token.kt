package cookers.com.authentication.domain.model

data class Token(
    val userId: String,
    val token: String,
    val refreshToken: String
)