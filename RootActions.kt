package com.abusalem.netsentinel.utils

import java.io.BufferedReader
import java.io.InputStreamReader

object RootActions {
    fun isRooted(): Boolean {
        val paths = arrayOf(
            "/system/bin/su", "/system/xbin/su",
            "/system/app/Superuser.apk", "/system/sbin/su"
        )
        return paths.any { path -> java.io.File(path).exists() }
    }

    fun runAsRoot(command: String): String {
        return try {
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", command))
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val output = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }
            output.toString()
        } catch (e: Exception) {
            "فشل في تنفيذ الأمر بصلاحيات الروت"
        }
    }
}
