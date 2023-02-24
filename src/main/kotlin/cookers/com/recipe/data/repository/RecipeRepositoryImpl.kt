package cookers.com.recipe.data.repository

import cookers.com.recipe.data.data_source.RecipeDb
import cookers.com.recipe.domain.model.Recipe
import cookers.com.recipe.domain.repository.RecipeRepository
import org.litote.kmongo.coroutine.CoroutineFindPublisher

class RecipeRepositoryImpl(
    private val recipeDb: RecipeDb
) : RecipeRepository {
    override suspend fun getRecipesForUser(email: String) =
        recipeDb.getRecipesForUser(email)

    override suspend fun getRecipesByUserId(id: String): CoroutineFindPublisher<Recipe> =
        recipeDb.getRecipesByUserId(id)

    override suspend fun registerRecipe(recipe: Recipe): Boolean =
        recipeDb.registerRecipe(recipe)

    override suspend fun getAllRecipes() = recipeDb.getAllRecipes()

    override suspend fun getRecipesFavorite(recipesFavorite: List<String>): CoroutineFindPublisher<Recipe> =
        recipeDb.getRecipesFavorite(recipesFavorite)
}