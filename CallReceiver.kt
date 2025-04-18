package com.abusalem.netsentinel.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import com.abusalem.netsentinel.service.CellLoggerService

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        val number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
            ?: intent.getStringExtra("incoming_number")

        if (state != null) {
            when (state) {
                TelephonyManager.EXTRA_STATE_RINGING -> {
                    Log.d("CallReceiver", "Ringing: $number")
                    CellLoggerService.logEvent(context, number, "مكالمة واردة (RINGING)")
                }
                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    Log.d("CallReceiver", "مكالمة نشطة: $number")
                    CellLoggerService.logEvent(context, number, "مكالمة نشطة (OFFHOOK)")
                }
                TelephonyManager.EXTRA_STATE_IDLE -> {
                    Log.d("CallReceiver", "انتهاء المكالمة")
                    CellLoggerService.logEvent(context, number, "انتهت المكالمة (IDLE)")
                }
            }
        }
    }
}
