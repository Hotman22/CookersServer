package cookers.com.authentication

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User (
    val userName: String,
    val name: String,
    val email: String,
    val password: String,
    @BsonId
    val id: String = ObjectId().toString()
)