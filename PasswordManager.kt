package com.abusalem.netsentinel.utils

import android.content.Context
import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object PasswordManager {
    private const val secretKey = "S3cr3tP@ssW0rDxX"
    private const val AES_MODE = "AES/CBC/PKCS5Padding"
    private const val PREF_NAME = "net_sentinel_prefs"
    private const val PASSWORD_KEY = "encrypted_password"

    private fun generateKey(): SecretKey {
        return SecretKeySpec(secretKey.toByteArray(), "AES")
    }

    fun saveEncryptedPassword(context: Context, password: String) {
        val key = generateKey()
        val cipher = Cipher.getInstance(AES_MODE)
        val iv = ByteArray(16) { 0 }
        cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(iv))
        val encrypted = cipher.doFinal(password.toByteArray())
        val encoded = Base64.encodeToString(encrypted, Base64.DEFAULT)
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putString(PASSWORD_KEY, encoded).apply()
    }

    fun checkPassword(context: Context, input: String): Boolean {
        val saved = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(PASSWORD_KEY, null) ?: return false

        val key = generateKey()
        val cipher = Cipher.getInstance(AES_MODE)
        val iv = ByteArray(16) { 0 }
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        val decrypted = cipher.doFinal(Base64.decode(saved, Base64.DEFAULT))
        return input == String(decrypted)
    }

    fun isPasswordSet(context: Context): Boolean {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .contains(PASSWORD_KEY)
    }
}
