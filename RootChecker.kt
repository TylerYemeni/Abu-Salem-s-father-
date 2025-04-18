package com.abusalem.netsentinel.utils

import java.io.File

object RootChecker {
    fun isDeviceRooted(): Boolean {
        val paths = arrayOf(
            "/sbin/su", "/system/bin/su", "/system/xbin/su",
            "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su"
        )
        return paths.any { File(it).exists() }
    }
}
