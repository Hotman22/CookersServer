package cookers.com.recipe.domain.repository

import cookers.com.recipe.domain.model.Recipe
import org.litote.kmongo.coroutine.CoroutineFindPublisher

interface RecipeRepository {
    suspend fun getRecipesForUser(email: String): List<Recipe>

    suspend fun registerRecipe(recipe: Recipe): Boolean

    suspend fun getAllRecipes(userId: String): CoroutineFindPublisher<Recipe>

    suspend fun getRecipesByUserId(id: String): CoroutineFindPublisher<Recipe>

    suspend fun getRecipesFavorite(recipesFavorite: List<String>): CoroutineFindPublisher<Recipe>

    suspend fun getRecipesFromSubscription(subscriptions: List<String>, recipeFavorites: MutableList<String>): CoroutineFindPublisher<Recipe>

    suspend fun getRecipesNotFromSubscription(
        userId: String,
        subscriptions: List<String>,
        recipeFavorites: MutableList<String>
    ): CoroutineFindPublisher<Recipe>

    suspend fun deleteRecipe(recipeId: String): Boolean
}