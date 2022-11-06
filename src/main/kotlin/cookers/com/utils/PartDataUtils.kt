package cookers.com.utils

import io.ktor.http.content.*
import java.io.File

fun PartData.FileItem.save(fileName: String): String {
    val fileBytes = this.streamProvider().readBytes()
    val folder = File("uploads/")
    folder.mkdir()
    File("uploads/$fileName").writeBytes(fileBytes)
    return fileName
}