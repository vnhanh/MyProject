package com.tma.movemind.ui.splash_screen

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.ResultReceiver
import android.util.Log

import com.tma.movemind.R
import kotlinx.android.synthetic.main.activity_splash.*
import vn.com.tma.custom_exoplayer.Constant.TAG_LOG
import vn.com.tma.vo_ngoc_hanh.network.download.*
import vn.com.tma.vo_ngoc_hanh.network.download.WriteResponseToFileService.Companion.EXTRA_FILE_NAME
import vn.com.tma.vo_ngoc_hanh.network.download.WriteResponseToFileService.Companion.EXTRA_RESULT_RECEIVER
import vn.com.tma.vo_ngoc_hanh.network.download.WriteResponseToFileService.Companion.EXTRA_SAVED_FOLDER_PATH
import java.io.File
import java.lang.ref.WeakReference

class SplashActivity : AppCompatActivity() {

    private lateinit var broadcastReceiver:NetworkManager
    private lateinit var downloadManager:DownloadManagerCustomize
//    private lateinit var notiManager:NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        initVars()
    }

//    @TargetApi(Build.VERSION_CODES.O)
//    private fun createChanelNoti() {
//        if(Util.getSDKVersion() >= Build.VERSION_CODES.O){
//            val name = "Download"
//            val description = "Download noti update"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(CHANNEL_ID, name, importance)
//            channel.description = description
//
//            notiManager = getSystemService(NotificationManager::class.java) as NotificationManager
//            notiManager.createNotificationChannel(channel)
//        }
//    }

    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    private fun initVars() {
        broadcastReceiver = NetworkManager()
        val intent = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(broadcastReceiver, intent)

        downloadManager = DownloadManagerCustomize(this)
        broadcastReceiver.addListener(downloadManager)
    }

    override fun onResume() {
        super.onResume()

        btn_download.setOnClickListener {
            if(isDownloading){
                downloadManager.pauseDownload()
                isDownloading = false
            }else{
                download()
            }
        }
    }

    private fun download() {
        val fileName = DownloadManagerCustomize.getFilename(url)
        val savedFolder = File(Environment.getExternalStorageDirectory(), "/MoveMind/")



//        val downloadBundle = DownloadBundle(url, savedFolder.path)
//        downloadBundle.updateListener = updateDownloadProgressListener
//        downloadBundle.changeStatesListener = changeStatusDownloadListener
//
//        downloadBundle.notiBundle = NotiSetupBundle(this, "DOWNLOAD ID", "MoveMind Download", "Download file", R.drawable.ic_file_download_black_24dp,
//                "Download file " + fileName, "", 100, 0)
//
//        downloadManager.startDownloadOrEnqueue(downloadBundle)
//
//        isDownloading = true
        callDownloadService(savedFolder, fileName)
        btn_download.text = "PAUSE"
    }

    private fun callDownloadService(savedFolder: File, fileName: String) {
        val intent = Intent()
        intent.setClass(this, WriteResponseToFileService::class.java)
        intent.putExtra(WriteResponseToFileService.EXTRA_DOWNLOAD, true)
        intent.putExtra(EXTRA_SAVED_FOLDER_PATH, savedFolder.path)
        intent.putExtra(EXTRA_FILE_NAME, fileName)
        intent.putExtra(EXTRA_RESULT_RECEIVER, MyResultReceiver(this, Handler()))
        startService(intent)
    }

    private var isDownloading = false

    private val url = "https://github.com/git-for-windows/git/releases/download/v2.21.0.windows.1/Git-2.21.0-64-bit.exe"
    private var subDownloadDisplay = ""
    private var KB = 1024
    private var MB = 1024 * 1024
    private var GB = 1024 * 1024 * 1024


    private val updateDownloadProgressListener = object : WriteResponseToFileTask.OnDownloadProgressValueListener{

        override fun onCalculateTotalDownload(total: Long) {
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

            subDownloadDisplay = String.format("%.2f %s", capacityDownload, unit)
        }

        override fun onUpdateProgress(progress: Int) {
            Log.d(TAG_LOG, "activity | download | progress: ${progress}")
            prg_bar_download.progress = progress
            tv_download_progress_value.text = String.format("%d %% of %s", progress, subDownloadDisplay)
        }
    }

    private val changeStatusDownloadListener = object : WriteResponseToFileTask.OnChangeStateListener {
        override fun onChangeState(status: String) {
            tv_download_progress_status.text = status
        }
    }

    private class MyResultReceiver(activity:SplashActivity, handler:Handler) :  ResultReceiver(handler) {
        private val weakActivity:WeakReference<SplashActivity>
        private val activity:SplashActivity?
        get() = weakActivity.get()

        init {
            weakActivity = WeakReference(activity)
        }

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
            when(resultCode){
                WriteResponseToFileService.RESULT_CODE_OK -> {
                    activity?.onReceiveOKBundle(resultData)
                }

                WriteResponseToFileService.RESULT_CODE_ERROR -> {

                }
            }
        }
    }

    private fun onReceiveOKBundle(bundle: Bundle?) {
        if(bundle == null) return
        if(bundle.containsKey(WriteResponseToFileService.KEY_PROGRESS)){
            val progress = bundle.getInt(WriteResponseToFileService.KEY_PROGRESS)
            prg_bar_download.progress = progress
        }
    }
}
