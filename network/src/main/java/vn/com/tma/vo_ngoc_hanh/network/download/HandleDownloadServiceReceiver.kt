package vn.com.tma.vo_ngoc_hanh.network.download

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import vn.com.tma.vo_ngoc_hanh.network.utils.NotiUtil.ACTION_NOTI_DOWNLOAD_STOP

class HandleDownloadServiceReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("LOG", "HandleDownloadServiceReceiver | onReceive")
        when(intent?.action){
            ACTION_NOTI_DOWNLOAD_STOP -> {
                val intent = Intent().apply {
                    putExtra("stop", true)
                }
                context?.startService(intent)
            }

            else -> {}
        }
    }
}