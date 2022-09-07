package cookers.com.authentication.login

data class LoginResponse(
    val token: String,
    val refreshToken: String
)