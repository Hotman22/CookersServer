package noteapp.ktor.com.data

import noteapp.ktor.com.data.collections.Note
import noteapp.ktor.com.data.collections.User
import noteapp.ktor.com.security.checkHashForPassword
import org.litote.kmongo.contains
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.setValue

private val client = KMongo.createClient().coroutine
private val databse = client.getDatabase("NotesDatabase")
private val users = databse.getCollection<User>()
private val notes = databse.getCollection<Note>()

suspend fun registerUser(user: User): Boolean = users.insertOne(user).wasAcknowledged()

suspend fun checkIfUserExists(email: String): Boolean =
    users.findOne(User::email eq email) != null

suspend fun checkPasswordForEmail(email: String, passwordToCheck: String): Boolean {
    val actualPassword = users.findOne(User::email eq email)?.password ?: return false
    return checkHashForPassword(passwordToCheck, actualPassword)
}

suspend fun getUser(email: String) = users.findOne(User::email eq email)

suspend fun getNotesForUser(email: String): List<Note> {
    return notes.find(Note::owners contains email).toList()
}

suspend fun saveNote(note: Note): Boolean {
    val noteExists = notes.findOneById(note.id) != null
    return if (noteExists) {
        notes.updateOneById(note.id, note).wasAcknowledged()
    } else {
        notes.insertOne(note).wasAcknowledged()
    }
}

suspend fun deleteNoteForUser(email: String, noteID: String): Boolean {
    val note = notes.findOne(Note::id eq noteID, Note::owners contains email)
    note?.let { note ->
        if(note.owners.size > 1) {
            // the note has multiple owners, so we just delete the email from the owners list
            val newOwners = note.owners - email
            //update the note with the new owners list
            val updateResult = notes.updateOne(Note::id eq note.id, setValue(Note::owners, newOwners))
            return updateResult.wasAcknowledged()
        }
        return notes.deleteOneById(note.id).wasAcknowledged()
    } ?: return false
}

suspend fun isOwnerOfNote(noteID: String, owner: String): Boolean {
    val note = notes.findOneById(noteID) ?: return false
    return owner in note.owners
}

suspend fun addOwnerToNote(noteID: String, owner: String): Boolean {
    val owners = notes.findOneById(noteID)?.owners ?: return false
    return notes.updateOneById(noteID, setValue(Note::owners, owners + owner)).wasAcknowledged()
}
