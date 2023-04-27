package cookers.com.search.data.repository

import cookers.com.authentication.data.data_source.AuthenticationDb
import cookers.com.authentication.domain.model.User
import cookers.com.recipe.data.data_source.RecipeDb
import cookers.com.recipe.domain.model.Recipe
import cookers.com.search.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val authDb : AuthenticationDb,
    private val recipeDb: RecipeDb
) : SearchRepository {
    override suspend fun findUserAndRecipeByQuery(query: String, userId: String): Pair<List<User>, List<Recipe>> {
        val users = authDb.findUsersByQuery(query, userId)
        val recipes = recipeDb.findRecipesByQuery(query, userId)
        return Pair(users, recipes)
    }
}