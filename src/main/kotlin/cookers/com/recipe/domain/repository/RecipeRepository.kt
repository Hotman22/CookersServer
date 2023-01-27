package cookers.com.recipe.domain.repository

import cookers.com.recipe.domain.model.Recipe
import org.litote.kmongo.coroutine.CoroutineFindPublisher

interface RecipeRepository {
    suspend fun getRecipesForUser(email: String): List<Recipe>

    suspend fun registerRecipe(recipe: Recipe): Boolean

    suspend fun getAllRecipes(): CoroutineFindPublisher<Recipe>

    suspend fun getRecipesByUserId(id: String): CoroutineFindPublisher<Recipe>
}