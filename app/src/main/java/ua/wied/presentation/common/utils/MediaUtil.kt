package ua.wied.presentation.common.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun makeVideoFile(context: Context): File {
    val moviesDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        ?: context.filesDir
    val filename = "VID_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
        .format(Date())}.mp4"
    return File(moviesDir, filename)
}