package cookers.com.authentication.domain.model.request

data class CreateUserRequest(
    val userName: String,
    val name: String,
    val email: String,
    val password: String,
)