package cookers.com.recipe.data.repository

import cookers.com.recipe.data.data_source.RecipeDb
import cookers.com.recipe.domain.model.Recipe
import cookers.com.recipe.domain.repository.RecipeRepository
import cookers.com.utils.database
import org.litote.kmongo.eq

class RecipeRepositoryImpl(
    private val recipeDb: RecipeDb
) : RecipeRepository {
    override suspend fun getRecipesForUser(email: String): List<Recipe> =
        recipeDb.getRecipesForUser(email)

    override suspend fun registerRecipe(recipe: Recipe): Boolean =
        recipeDb.registerRecipe(recipe)
}