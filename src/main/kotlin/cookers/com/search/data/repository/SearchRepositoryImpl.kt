package cookers.com.search.data.repository

import cookers.com.authentication.data.data_source.AuthenticationDb
import cookers.com.authentication.domain.model.User
import cookers.com.recipe.data.data_source.RecipeDb
import cookers.com.recipe.domain.model.Recipe
import cookers.com.search.domain.repository.SearchRepository
import org.litote.kmongo.coroutine.CoroutineFindPublisher

class SearchRepositoryImpl(
    private val authDb : AuthenticationDb,
    private val recipeDb: RecipeDb
) : SearchRepository {
    override suspend fun findUsers(query: String, userId: String): CoroutineFindPublisher<User> =
        authDb.findUsersByQuery(query, userId)

    override suspend fun findRecipes(query: String, userId: String): CoroutineFindPublisher<Recipe> =
        recipeDb.findRecipesByQuery(query, userId)
}