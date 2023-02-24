package cookers.com.recipe.data.data_source

import cookers.com.recipe.domain.model.Recipe
import cookers.com.recipe.domain.model.Recipes
import cookers.com.utils.database
import org.litote.kmongo.`in`
import org.litote.kmongo.contains
import org.litote.kmongo.coroutine.CoroutineFindPublisher
import org.litote.kmongo.eq
import org.litote.kmongo.util.idValue


class RecipeDb {
    private val recipes = database.getCollection<Recipe>()

    suspend fun registerRecipe(recipe: Recipe): Boolean = recipes.insertOne(recipe).wasAcknowledged()

    suspend fun getRecipesForUser(email: String): List<Recipe> =
        recipes.find(Recipe::userName.eq(email)).toList()

    fun getRecipesByUserId(id: String): CoroutineFindPublisher<Recipe> =
        recipes.find(Recipe::userId.eq(id))

    fun getAllRecipes() = recipes.find()

    fun getRecipesFavorite(recipesFavorite: List<String>): CoroutineFindPublisher<Recipe> =
        recipes.find(Recipe::id.`in`(recipesFavorite))
}