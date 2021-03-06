//package cookers.com
//
//import cookers.com.authentication.AddOwnerRequest
//import cookers.com.authentication.DeleteNoteRequest
//import cookers.com.authentication.JwtConfig
//import cookers.com.authentication.createuser.checkIfUserExists
//import cookers.com.utils.SimpleResponse
//import io.ktor.http.HttpStatusCode.Companion.BadRequest
//import io.ktor.http.HttpStatusCode.Companion.Conflict
//import io.ktor.http.HttpStatusCode.Companion.OK
//import io.ktor.server.application.*
//import io.ktor.server.auth.*
//import io.ktor.server.request.*
//import io.ktor.server.response.*
//import io.ktor.server.routing.*
//import cookers.com.addOwnerToNote as addOwnerToNote1
//
//fun Route.noteRoutes() {
//    route("/getNotes") {
//            get {
//                val email = (call.authentication.principal as JwtConfig.JwtUser).userName
//                val notes = getNotesForUser(email)
//                call.respond(OK, notes)
//            }
//    }
//
//    route("/addNotes") {
//            post {
//                val note = try {
//                    call.receive<Note>()
//                } catch(e: ContentTransformationException) {
//                    call.respond(BadRequest)
//                    return@post
//                }
//                if (saveNote(note)) {
//                    call.respond(OK)
//                } else {
//                    call.respond(Conflict)
//                }
//        }
//    }
//
//    route("/deleteNote") {
//        authenticate {
//            post {
//                val email = (call.authentication.principal as JwtConfig.JwtUser).userName
//                val request = try {
//                    call.receive<DeleteNoteRequest>()
//                } catch(e: ContentTransformationException) {
//                    call.respond(BadRequest)
//                    return@post
//                }
//                if(deleteNoteForUser(email, request.id)) {
//                    call.respond(OK)
//                } else {
//                    call.respond(Conflict)
//                }
//            }
//        }
//    }
//
//    route("/addOwnerToNote") {
//        authenticate {
//            post {
//                val request = try {
//                    call.receive<AddOwnerRequest>()
//                } catch(e: ContentTransformationException) {
//                    call.respond(BadRequest)
//                    return@post
//                }
//                if(!checkIfUserExists(request.owner, "")) {
//                    call.respond(OK, SimpleResponse(false, "No user with this E-Mail exists"))
//                    return@post
//                }
//                if(isOwnerOfNote(request.noteID, request.owner)) {
//                    call.respond(OK, SimpleResponse(false, "This user is already an owner of this note"))
//                    return@post
//                }
//                if(addOwnerToNote1(request.noteID, request.owner)) {
//                    call.respond(OK, SimpleResponse(true, "${request.owner} can now see this note"))
//                } else {
//                    call.respond(Conflict)
//                }
//            }
//        }
//    }
//}