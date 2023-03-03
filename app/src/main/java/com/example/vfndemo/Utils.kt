package com.example.vfndemo

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.media.MediaFormat
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.ui.semantics.Role.Companion.Image
import java.io.*

var name: String? = null
const val VIDEO_DIR_NAME = "videoItams"
const val IMAGE_DIR_NAME = "ImageItems"
const val TEMPLATES_DIR_NAME = "VideoPlayer"
const val WM_DIR_NAME = "marks"
fun Context.saveToCacheAndCopy(inputStream: InputStream, mediaFormat: MediaFormat): String? {
    val filePath = getCachePath(".${mediaFormat}")

    val file = File(filePath)

    try {
        val outputStream: OutputStream = FileOutputStream(file)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()
    } catch (ignore: IOException) {
        return null
    }

    val path = file.absolutePath

//    PrefUtils.saveCacheFile(path)

    return path

}
fun Context.getCachePath(format: String): String {
    return cacheDir?.absolutePath + File.separator + System.currentTimeMillis() + format
}

fun Context.scanPath(path: String) {
    MediaScannerConnection.scanFile(this, arrayOf(path), null, null)
}

fun Context.copyMediaToGallery(path: String, type: Media.Type, format: Media.Format, isTemplate: Boolean = false): String {
    val name = System.currentTimeMillis().toString() + ".${format.name}"

    var savePath = ""

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        try {
            val resolver: ContentResolver = contentResolver

            val contentValues = ContentValues()

            val uri = when (type) {
                Media.Type.Image -> {
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/$format")

                    if (format == Media.Format.gif) {
                        val location = if (isTemplate) TEMPLATES_DIR_NAME else VIDEO_DIR_NAME

                        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MOVIES + "/" + location)
                    } else {
                        val location = if (isTemplate) TEMPLATES_DIR_NAME else IMAGE_DIR_NAME

                        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + location)
                    }

                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                }
                Media.Type.WM_Image -> {
                    val location = if (isTemplate) TEMPLATES_DIR_NAME else WM_DIR_NAME

                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/$format")
                    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/" + location)

                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                }
                Media.Type.Video -> {
                    val location = if (isTemplate) TEMPLATES_DIR_NAME else VIDEO_DIR_NAME

                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/$format")
                    contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_MOVIES + "/" + location)

                    resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)

                }
                else -> {
                    Uri.parse("")
                }
            }

            uri?.let { uriNotNull ->
                val outputStream = resolver.openOutputStream(uriNotNull)

                try {
                    val inputStream = FileInputStream(path)
                    val buf = ByteArray(1024)
                    var len: Int
                    while (inputStream.read(buf).also { len = it } > 0) outputStream?.write(buf, 0, len)
                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    outputStream?.flush()
                    outputStream?.close()
                }

                savePath = uriNotNull.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } else {
        val fullPath = if (type == Media.Type.Video) {
            val location = if (isTemplate) TEMPLATES_DIR_NAME else VIDEO_DIR_NAME
            Environment.getExternalStorageDirectory().absolutePath + "/" + location
        } else if (type == Media.Type.Image) {
            if (format == Media.Format.gif) {
                val location = if (isTemplate) TEMPLATES_DIR_NAME else VIDEO_DIR_NAME
                Environment.getExternalStorageDirectory().absolutePath + "/" + location
            } else {
                val location = if (isTemplate) TEMPLATES_DIR_NAME else IMAGE_DIR_NAME
                Environment.getExternalStorageDirectory().absolutePath + "/" + location
            }
        } else if (type == Media.Type.WM_Image) {
            val location = if (isTemplate) TEMPLATES_DIR_NAME else WM_DIR_NAME
            Environment.getExternalStorageDirectory().absolutePath + "/" + location
        } else {
            ""
        }

        val dir = File(fullPath)

        if (!dir.exists()) {
            dir.mkdirs()
        }

        val videoFile = File(fullPath, name)

        val inputStream = FileInputStream(path)
        val outputStream = FileOutputStream(videoFile)

        try {
            val buf = ByteArray(1024)
            var len: Int
            while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            outputStream.close()
            inputStream.close()
        }

        savePath = videoFile.absolutePath
    }

    scanPath(savePath)

    return savePath
}

fun Context.saveToCacheAndCopy(inputStream: InputStream, mediaFormat: Media.Format): String? {
    val filePath = getCachePath(".${mediaFormat.name}")

    val file = File(filePath)

    try {
        val outputStream: OutputStream = FileOutputStream(file)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()
    } catch (ignore: IOException) {
        return null
    }

    val path = file.absolutePath

//    PrefUtils.saveCacheFile(path)

    return path
}

fun getFormat(str: String): Media.Format {
    val type = str.split('.').last().lowercase()

    return Media.Format.valueOf(type)
}