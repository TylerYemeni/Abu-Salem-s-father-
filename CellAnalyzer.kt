import com.abusalem.netsentinel.utils.RootActions

...

fun analyze(context: Context) {
    val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val cells = tm.allCellInfo
    val file = File(context.filesDir, "cell_towers_log.csv")
    val writer = FileWriter(file, true)

    for (info in cells) {
        val type = if (info is CellInfoGsm) "GSM" else if (info is CellInfoLte) "LTE" else "UNKNOWN"
        val mcc = when (info) {
            is CellInfoGsm -> info.cellIdentity.mcc
            is CellInfoLte -> info.cellIdentity.mcc
            else -> -1
        }
        val mnc = when (info) {
            is CellInfoGsm -> info.cellIdentity.mnc
            is CellInfoLte -> info.cellIdentity.mnc
            else -> -1
        }
        val cid = when (info) {
            is CellInfoGsm -> info.cellIdentity.cid
            is CellInfoLte -> info.cellIdentity.ci
            else -> -1
        }
        val lac = when (info) {
            is CellInfoGsm -> info.cellIdentity.lac
            is CellInfoLte -> info.cellIdentity.tac
            else -> -1
        }
        val signal = when (info) {
            is CellInfoGsm -> info.cellSignalStrength.dbm
            is CellInfoLte -> info.cellSignalStrength.dbm
            else -> 0
        }

        val root = if (RootActions.isRooted()) "Rooted" else "NoRoot"
        val extraRootInfo = if (RootActions.isRooted()) {
            RootActions.runAsRoot("dumpsys telephony.registry | grep cell")
        } else {
            "No Root Access"
        }

        val log = "${System.currentTimeMillis()},$type,$mcc,$mnc,$lac,$cid,$signal,$root,${extraRootInfo.replace("\n", "|")}\n"
        writer.write(log)
    }

    writer.close()
}
