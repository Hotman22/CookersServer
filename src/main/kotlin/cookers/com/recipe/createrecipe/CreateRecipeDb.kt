package cookers.com.recipe.createrecipe

import cookers.com.utils.database

private val recipes = database.getCollection<Recipe>()

suspend fun registerRecipe(recipe: Recipe): Boolean = recipes.insertOne(recipe).wasAcknowledged()
