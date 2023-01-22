package cookers.com.recipe.data.data_source

import cookers.com.recipe.domain.model.Recipe
import cookers.com.utils.database
import org.litote.kmongo.eq

class RecipeDb {
    private val recipes = database.getCollection<Recipe>()

    suspend fun registerRecipe(recipe: Recipe): Boolean = recipes.insertOne(recipe).wasAcknowledged()

    suspend fun getRecipesForUser(email: String): List<Recipe> =
        recipes.find(Recipe::userName.eq(email)).toList()

    fun getAllRecipes() = recipes.find()
}