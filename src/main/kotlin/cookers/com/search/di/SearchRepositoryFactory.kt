package cookers.com.search.di

import cookers.com.authentication.data.data_source.AuthenticationDb
import cookers.com.recipe.data.data_source.RecipeDb
import cookers.com.search.data.repository.SearchRepositoryImpl

object SearchRepositoryFactory {
    fun make() = SearchRepositoryImpl(AuthenticationDb(), RecipeDb())
}