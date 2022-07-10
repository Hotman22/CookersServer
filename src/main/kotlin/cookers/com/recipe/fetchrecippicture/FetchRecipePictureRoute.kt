package cookers.com.recipe.fetchrecippicture

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.fetchRecipePictureRoute() {
    authenticate {
        get("/recipe/fetchrecipepicture") {
            val picturePath = call.request.queryParameters["picturePath"]
            call.respondFile(File("./uploads/$picturePath"))
        }
    }
}