package cookers.com.recipe.fetchrecippicture

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.fetchRecipePictureRoute() {
        get("/recipe/fetchrecipepicture") {
            val picturePath = call.request.queryParameters["picturePath"]
            call.respondFile(File("./uploads/$picturePath"))
    }
}