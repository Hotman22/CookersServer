package cookers.com.search.domain.model

import cookers.com.authentication.domain.model.User
import cookers.com.recipe.domain.model.Recipe

data class SearchUserAndRecipeResult(
    val users: List<User>,
    val recipes: List<Recipe>
)

