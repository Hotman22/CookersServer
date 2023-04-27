package cookers.com.search.domain.repository

import cookers.com.authentication.domain.model.User
import cookers.com.recipe.domain.model.Recipe

interface SearchRepository {
  suspend fun findUserAndRecipeByQuery(query: String, userId: String): Pair<List<User>, List<Recipe>>
}