package cookers.com.authentication.domain.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User (
    val userName: String,
    val name: String,
    val email: String,
    val password: String,
    val pictureFilePath: String? = null,
    val subscriptions: MutableList<String> = mutableListOf(),
    @BsonId
    val id: String = ObjectId().toString()
)