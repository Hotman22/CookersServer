package cookers.com.recipe.domain.repository

import cookers.com.recipe.domain.model.Recipe

interface RecipeRepository {
    suspend fun getRecipesForUser(email: String): List<Recipe>

    suspend fun registerRecipe(recipe: Recipe): Boolean
}