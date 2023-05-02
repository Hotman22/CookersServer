package cookers.com.search.domain.repository

import cookers.com.authentication.domain.model.User
import cookers.com.recipe.domain.model.Recipe
import org.litote.kmongo.coroutine.CoroutineFindPublisher

interface SearchRepository {
  suspend fun findUsers(query: String, userId: String): CoroutineFindPublisher<User>
  suspend fun findRecipes(query: String, userId: String): CoroutineFindPublisher<Recipe>
}