package cookers.com.recipe.fetchrecipe

import cookers.com.recipe.Recipe
import cookers.com.utils.database
import org.litote.kmongo.eq

private val recipes = database.getCollection<Recipe>()

suspend fun getRecipesForUser(email: String): List<Recipe> {
    return recipes.find(Recipe::userName.eq(email)).toList()
}