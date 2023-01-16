package cookers.com.recipe.domain.model

data class Recipe(
    val title: String,
    val steps: List<String>,
    val ingredients: List<String>,
    var planningTime: String,
    var planningUnit: String,
    var cookingTime: String,
    var cookingUnit: String,
    var peopleNumber: String,
    var dishType: String,
    val advice: String,
    val filePath: String,
    val userId: String,
    val userName: String
)