package cookers.com.recipe

data class Recipe(
    val title: String,
    val steps: List<String>,
    val ingredients: List<String>,
    val advice: String,
    val filePath: String,
    val userId: String,
    val userName: String
)