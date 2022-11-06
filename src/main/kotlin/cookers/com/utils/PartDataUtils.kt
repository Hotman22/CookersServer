package cookers.com.utils

import io.ktor.http.content.*
import java.io.File

fun PartData.FileItem.save(fileName: String, pathName: String): String {
    val fileBytes = this.streamProvider().readBytes()
    val folder = File(pathName)
    folder.mkdir()
    File("$pathName$fileName").writeBytes(fileBytes)
    return fileName
}