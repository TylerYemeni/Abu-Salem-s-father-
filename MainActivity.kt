package com.abusalem.netsentinel.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.abusalem.netsentinel.R
import com.abusalem.netsentinel.utils.RootChecker

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val welcomeText = findViewById<TextView>(R.id.welcomeText)
        val rootStatus = findViewById<TextView>(R.id.rootStatus)

        welcomeText.text = "مرحبًا بك في NetSentinel AI يا أبو سالم!"
        rootStatus.text = if (RootChecker.isDeviceRooted()) {
            "الجهاز يحتوي على صلاحيات روت"
        } else {
            "الجهاز غير مروّت"
        }
    }
}
