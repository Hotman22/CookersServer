package cookers.com.recipe.domain.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class Recipe(
    val title: String,
    val steps: List<String>,
    val ingredients: List<String>,
    var planningTime: String?,
    var planningUnit: String?,
    var cookingTime: String?,
    var cookingUnit: String?,
    var peopleNumber: String?,
    var dishType: String?,
    val advice: String,
    val filePath: String,
    val userId: String,
    val userName: String,
    @BsonId
    @field:BsonProperty(useDiscriminator = true)
    val id: String = ObjectId().toString(),
)