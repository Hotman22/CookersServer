package cookers.com.recipe.data.data_source

import cookers.com.recipe.domain.model.Recipe
import cookers.com.utils.database
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineFindPublisher


class RecipeDb {
    private val recipes = database.getCollection<Recipe>()

    suspend fun registerRecipe(recipe: Recipe): Boolean = recipes.insertOne(recipe).wasAcknowledged()

    suspend fun getRecipesForUser(email: String): List<Recipe> =
        recipes.find(Recipe::userName.eq(email)).toList()

    fun getRecipesByUserId(id: String): CoroutineFindPublisher<Recipe> =
        recipes.find(Recipe::userId.eq(id))

    fun getAllRecipes(userId: String) = recipes.find(Recipe::userId ne userId)

    fun getRecipesFavorite(recipesFavorite: List<String>): CoroutineFindPublisher<Recipe> =
        recipes.find(Recipe::id.`in`(recipesFavorite))

    fun getRecipesFromSubscription(subscriptions: List<String>, recipeFavorites: MutableList<String>): CoroutineFindPublisher<Recipe> =
        recipes.find(and(Recipe::userId.`in`(subscriptions), Recipe::id.nin(recipeFavorites)))

    fun getRecipesNotFromSubscription(userId: String, subscriptions: List<String>, recipeFavorites: MutableList<String>): CoroutineFindPublisher<Recipe> =
        recipes.find(and(Recipe::userId.nin(subscriptions), Recipe::userId ne userId, Recipe::id.nin(recipeFavorites)))
}