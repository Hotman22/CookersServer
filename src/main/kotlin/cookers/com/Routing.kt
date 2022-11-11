package cookers.com

import cookers.com.authentication.di.AuthenticationRepositoryFactory
import cookers.com.authentication.domain.route.*
import cookers.com.authentication.domain.util.JwtConfig
import cookers.com.recipe.domain.route.createRecipe
import cookers.com.recipe.di.RecipeRepositoryFactory
import cookers.com.recipe.domain.route.fetchRecipe
import cookers.com.recipe.domain.route.fetchRecipePicture
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val jwtConfig = JwtConfig(System.getenv("KTOR_TODOLIST_JWT_SECRET") ?: "default_value")
val authRepository = AuthenticationRepositoryFactory.make()
val recipeRepository = RecipeRepositoryFactory.make()

fun Application.configureRouting() {
    configureModule()

    // Starting point for a Ktor app:
    routing {
        get("/") {
            call.respondText("Hello World World New!")
        }
    }
}

private fun Application.configureModule() {
    install(DoubleReceive)
    install(Authentication) {
        jwt {
            jwtConfig.configureKtorFeature(this)
        }
    }
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        //authentication route
        createUser(authRepository)
        login(authRepository)
        fetchUser(authRepository)
        refreshToken(authRepository)
        updateUser(authRepository)
        fetchUserPicture()
        //recipe route
        createRecipe(recipeRepository)
        fetchRecipe(recipeRepository)
        fetchRecipePicture()
    }
}
