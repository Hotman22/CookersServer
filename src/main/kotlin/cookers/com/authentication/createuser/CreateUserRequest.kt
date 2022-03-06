package cookers.com.authentication.createuser

data class CreateUserRequest(
    val userName: String,
    val name: String,
    val email: String,
    val password: String
)