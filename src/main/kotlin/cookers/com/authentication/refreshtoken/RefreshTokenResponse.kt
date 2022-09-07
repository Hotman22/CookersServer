package cookers.com.authentication.refreshtoken

data class RefreshTokenResponse(
    val token: String,
    val refreshToken: String
)