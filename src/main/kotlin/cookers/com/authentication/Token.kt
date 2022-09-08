package cookers.com.authentication

data class Token(
    val userId: String,
    val token: String,
    val refreshToken: String
)