package com.abusalem.netsentinel.service

import android.content.Context
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

object LogToFile {
    private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    fun save(context: Context, data: String) {
        val logFile = File(context.filesDir, "cell_log.txt")
        val writer = FileWriter(logFile, true)
        writer.append("\n--- ${sdf.format(Date())} ---\n")
        writer.append(data)
        writer.append("\n")
        writer.close()
    }
}
