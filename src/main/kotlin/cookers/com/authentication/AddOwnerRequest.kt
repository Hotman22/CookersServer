package cookers.com.authentication

data class AddOwnerRequest(
    val noteID: String,
    val owner: String
)