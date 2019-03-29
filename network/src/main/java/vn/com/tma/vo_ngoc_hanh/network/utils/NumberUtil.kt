package vn.com.tma.vo_ngoc_hanh.network.utils

object NumberUtil{
    private var KB = 1024
    private var MB = 1024 * 1024
    private var GB = 1024 * 1024 * 1024

    // convert total bytes to string displaying such as 20.34 MB, 1.02 GB
    fun calculateCapacityUnit(total: Long) : String {
        var capacityDownload:Float
        var unit:String

        if(total >= GB){
            capacityDownload = total * 1.0f / GB
            unit = "GB"
        }else if(total >= MB){
            capacityDownload = total * 1.0f / MB
            unit = "MB"
        }else{
            capacityDownload = total * 1.0f / KB
            unit = "KB"
        }

        return String.format("%.2f %s", capacityDownload, unit)
    }
}