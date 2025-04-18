package com.abusalem.netsentinel.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abusalem.netsentinel.R
import com.abusalem.netsentinel.utils.PasswordManager

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val loginBtn = findViewById<Button>(R.id.loginButton)

        if (!PasswordManager.isPasswordSet(this)) {
            PasswordManager.saveEncryptedPassword(this, "AbuSalem@2025")
        }

        loginBtn.setOnClickListener {
            val enteredPassword = passwordInput.text.toString()
            if (PasswordManager.checkPassword(this, enteredPassword)) {
                Toast.makeText(this, "تم تسجيل الدخول", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "كلمة المرور غير صحيحة", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
