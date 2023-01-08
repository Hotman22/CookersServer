package cookers.com.utils

import com.mongodb.ConnectionString
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val client = KMongo.createClient(ConnectionString("mongodb+srv://Hakim:Csun8z2b!@cluster0.mcnrh.mongodb.net/test")).coroutine
val database = client.getDatabase("NotesDatabase")