package com.abusalem.netsentinel.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abusalem.netsentinel.R
import com.abusalem.netsentinel.service.CellAnalyzer
import com.abusalem.netsentinel.service.LocationLogger
import com.abusalem.netsentinel.utils.RootActions
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val welcomeText = findViewById<TextView>(R.id.welcomeText)
        val rootStatus = findViewById<TextView>(R.id.rootStatus)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        welcomeText.text = "مرحبًا بك في NetSentinel AI يا أبو سالم!"

        val btnStartLocation = findViewById<Button>(R.id.btnStartLocation)
        val btnOpenMap = findViewById<Button>(R.id.btnOpenMap)
        val btnCheckRoot = findViewById<Button>(R.id.btnCheckRoot)
        val btnAnalyzeCell = findViewById<Button>(R.id.btnAnalyzeCell)

        btnStartLocation.setOnClickListener {
            LocationLogger.startLogging(this)
            Toast.makeText(this, "بدأ تسجيل الموقع...", Toast.LENGTH_SHORT).show()
        }

        btnOpenMap.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        btnCheckRoot.setOnClickListener {
            val rooted = RootActions.isRooted()
            val output = RootActions.runAsRoot("whoami")
            rootStatus.text = if (rooted) {
                "الجهاز يحتوي على صلاحيات روت ✅ ($output)"
            } else {
                "الجهاز غير مروّت ❌"
            }
        }

        btnAnalyzeCell.setOnClickListener {
            val rooted = RootActions.isRooted()
            val suCheck = if (rooted) RootActions.runAsRoot("whoami") else "NoRoot"

            val summary = "تحليل الشبكة جارٍ...\nصلاحية روت: ${if (rooted) "مفعلة ✅ ($suCheck)" else "غير مفعلة ❌"}"
            resultTextView.text = summary

            CellAnalyzer.analyze(this)

            val logFile = File(filesDir, "cell_towers_log.csv")
            if (logFile.exists()) {
                val lastLines = logFile.readLines().takeLast(3).joinToString("\n")
                resultTextView.text = "$summary\n\nآخر البيانات:\n$lastLines"
            } else {
                resultTextView.text = "$summary\nلم يتم العثور على ملف السجل."
            }
        }
    }
}
