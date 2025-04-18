package com.abusalem.netsentinel.service

import android.content.Context
import android.telephony.TelephonyManager
import android.telephony.CellInfoGsm
import android.telephony.CellInfo
import android.telephony.SubscriptionManager
import android.util.Log

object CellLoggerService {

    fun logEvent(context: Context, number: String?, event: String) {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val cellInfoList = tm.allCellInfo

        val info = StringBuilder()
        info.append("رقم: ${number ?: "غير معروف"}\n")
        info.append("الحدث: $event\n")

        for (cellInfo in cellInfoList) {
            if (cellInfo is CellInfoGsm) {
                val cellIdentity = cellInfo.cellIdentity
                info.append("MCC: ${cellIdentity.mcc}\n")
                info.append("MNC: ${cellIdentity.mnc}\n")
                info.append("LAC: ${cellIdentity.lac}\n")
                info.append("CID: ${cellIdentity.cid}\n")
            }
        }

        LogToFile.save(context, info.toString())
    }
}
