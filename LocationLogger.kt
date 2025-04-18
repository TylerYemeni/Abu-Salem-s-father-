package com.abusalem.netsentinel.service

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

object LocationLogger {
    private var locationManager: LocationManager? = null
    private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    @SuppressLint("MissingPermission")
    fun startLogging(context: Context) {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager?.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            60_000L, // كل 60 ثانية
            10f,
            object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    saveToFile(context, location)
                }

                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onProviderDisabled(provider: String) {}
            }
        )
    }

    private fun saveToFile(context: Context, location: Location) {
        val file = File(context.filesDir, "log_location.csv")
        val fw = FileWriter(file, true)
        val data = "${sdf.format(Date())},${location.latitude},${location.longitude}\n"
        fw.write(data)
        fw.close()
    }
}
