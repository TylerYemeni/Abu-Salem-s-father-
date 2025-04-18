package com.abusalem.netsentinel.service

import android.content.Context
import android.telephony.*
import android.util.Log
import java.io.File
import java.io.FileWriter

object CellAnalyzer {

    fun analyze(context: Context) {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val cells = tm.allCellInfo
        val file = File(context.filesDir, "cell_towers_log.csv")
        val writer = FileWriter(file, true)

        for (info in cells) {
            if (info is CellInfoGsm) {
                val cid = info.cellIdentity.cid
                val lac = info.cellIdentity.lac
                val mcc = info.cellIdentity.mcc
                val mnc = info.cellIdentity.mnc
                val dbm = info.cellSignalStrength.dbm

                val line = "${System.currentTimeMillis()},GSM,$mcc,$mnc,$lac,$cid,$dbm\n"
                writer.write(line)
                Log.d("CellAnalyzer", line)
            }
            if (info is CellInfoLte) {
                val cell = info.cellIdentity
                val signal = info.cellSignalStrength
                val line = "${System.currentTimeMillis()},LTE,${cell.mcc},${cell.mnc},${cell.tac},${cell.ci},${signal.dbm}\n"
                writer.write(line)
                Log.d("CellAnalyzer", line)
            }
        }

        writer.close()
    }
}
