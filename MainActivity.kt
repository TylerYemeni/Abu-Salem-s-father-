package com.abusalem.netsentinel.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abusalem.netsentinel.R
import com.abusalem.netsentinel.service.LocationLogger
import com.abusalem.netsentinel.utils.RootActions

class MainActivity : AppCompatActivity() {

    private lateinit var rootStatus: TextView
    private lateinit var btnLocation: Button
    private lateinit var btnMap: Button
    private lateinit var btnRootCheck: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootStatus = findViewById(R.id.rootStatus)
        btnLocation = findViewById(R.id.btnStartLocation)
        btnMap = findViewById(R.id.btnOpenMap)
        btnRootCheck = findViewById(R.id.btnCheckRoot)

        btnLocation.setOnClickListener {
            LocationLogger.startLogging(this)
            Toast.makeText(this, "بدأ تسجيل الموقع...", Toast.LENGTH_SHORT).show()
        }

        btnMap.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        btnRootCheck.setOnClickListener {
            val rooted = RootActions.isRooted()
            val output = RootActions.runAsRoot("whoami")
            rootStatus.text = if (rooted) {
                "الجهاز مروّت ✅\nأمر su نفّذ: $output"
            } else {
                "الجهاز غير مروّت ❌"
            }
        }
    }
}
