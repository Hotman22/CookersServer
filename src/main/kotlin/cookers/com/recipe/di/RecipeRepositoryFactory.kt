package cookers.com.recipe.di

import cookers.com.recipe.data.data_source.RecipeDb
import cookers.com.recipe.data.repository.RecipeRepositoryImpl

object RecipeRepositoryFactory {
    fun make() = RecipeRepositoryImpl(RecipeDb())
}