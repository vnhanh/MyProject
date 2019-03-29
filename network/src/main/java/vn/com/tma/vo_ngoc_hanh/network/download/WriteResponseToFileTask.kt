package vn.com.tma.vo_ngoc_hanh.network.download

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.util.Log
import okhttp3.ResponseBody
import vn.com.tma.vo_ngoc_hanh.network.R
import vn.com.tma.vo_ngoc_hanh.network.download.DownloadManagerCustomize.Companion.STATUS_DOWNLOAD_COMPLETED
import vn.com.tma.vo_ngoc_hanh.network.notification.NotiSetupBundle
import vn.com.tma.vo_ngoc_hanh.network.utils.NotiUtil
import vn.com.tma.vo_ngoc_hanh.network.utils.NumberUtil
import java.io.*
import java.lang.ref.WeakReference
import java.util.*

class WriteResponseToFileTask (val savedFolderPath:String, val fileName:String, val notiBundle:NotiSetupBundle?=null) : AsyncTask<ResponseBody, Bundle, Void>() {
    private val TAG = "LOG"
    private val DEFAULT_BUFFER = 4096

    // vars
    private var notiManager:NotificationManager?=null
    private var builder:NotificationCompat.Builder?=null


    init {
        notiBundle?.also { notiBundle->
            notiBundle.context?.also {context ->
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    val channelID = notiBundle.channelID
                    val name = notiBundle.notiName
                    val description = notiBundle.notiDescription
                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel(channelID, name, importance)
                    channel.description = description
                    notiManager = (context.getSystemService(NotificationManager::class.java) as NotificationManager).apply {
                        createNotificationChannel(channel)
                    }
                }else{
                    notiManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                }
            }
        }
    }

    override fun doInBackground(vararg params: ResponseBody): Void? {
        val responseBody = params[0]

        try {
            val folder = File(savedFolderPath)
            if(!folder.exists()){
                folder.mkdirs()
            }

            val savedFile = File(folder, fileName)

            synchronized(savedFile, {
                if(!savedFile.exists())
                    savedFile.createNewFile()

                var inputStream: InputStream? = null
                var outputStream: OutputStream? = null

                try {
                    val downloadedLength = savedFile.length()
                    val fileReader = ByteArray(DEFAULT_BUFFER)

                    val downloadLength = responseBody.contentLength()
                    val totalLength = downloadedLength + downloadLength

                    if(notiBundle!=null){
                        totalStrDisplay = NumberUtil.calculateCapacityUnit(totalLength)
                    }

                    publishProgress(generateLongBundle(KEY_TOTAL, totalLength))

                    var progress:Int = (downloadedLength * 100 / totalLength).toInt()
                    var preProgress = progress

                    publishProgress(generateIntBundle(KEY_PROGRESS, progress))

                    if(progress == 100) {
                        publishProgress(generateStringBundle(KEY_STATUS,  DownloadManagerCustomize.STATUS_FILE_ALREADY_DOWNLOADED))
                        return null
                    }

                    Log.d(TAG, "WriteResponseToFileTask | writeResponseBodyToDisk | fileSize: ${totalLength}")

                    var fileSizeDownloaded: Long = downloadedLength // total downloaded lenth

                    inputStream = responseBody.byteStream()

                    outputStream = FileOutputStream(savedFile, true)

                    while (true) {
                        val read = inputStream!!.read(fileReader)

                        if (read == -1) {
                            break
                        }

                        outputStream.write(fileReader, 0, read)  // write bytes into file

                        fileSizeDownloaded += read.toLong()
                        progress = (fileSizeDownloaded * 100 / totalLength).toInt()

                        if(progress != preProgress){
                            Log.d(TAG, "progress: ${progress} | preProgress: ${preProgress}")
                            preProgress = progress

                            publishProgress(generateIntBundle(KEY_PROGRESS, progress))
                        }

                        if(progress == 100) isSuccess = true
                    }

                    outputStream.flush()

                } catch (e: IOException) {
                    Log.d(TAG, "DownloadUsecase | error: ${e}")
                    publishProgress(generateStringBundle(KEY_STATUS, DownloadManagerCustomize.ERROR_DOWNLOAD_INTERRUPTED))
                } finally {
                    inputStream?.apply { close() }
                    outputStream?.apply { close() }
                }
            })
        } catch (e: IOException) {
            Log.d(TAG, "WriteResponseToFileTask | error: ${e}")
            publishProgress(generateStringBundle(KEY_STATUS, DownloadManagerCustomize.ERROR_IO_EXCEPTION))
        }

        return null
    }

    private fun generateIntBundle(key: String, value: Int): Bundle {
        val bundle = Bundle()
        bundle.putInt(key, value)
        return bundle
    }

    private fun generateLongBundle(key: String, value: Long): Bundle {
        val bundle = Bundle()
        bundle.putLong(key, value)
        return bundle
    }

    private fun generateStringBundle(key: String, value: String): Bundle {
        val bundle = Bundle()
        bundle.putString(key, value)
        return bundle
    }

    override fun onProgressUpdate(vararg values: Bundle) {
        super.onProgressUpdate(*values)
        val bundle = values[0]

        if(bundle.containsKey(KEY_TOTAL)){
            handleTotalDownloadMessage(bundle.getLong(KEY_TOTAL))
        }else if(bundle.containsKey(KEY_PROGRESS)){
            handleProgressMessage(bundle.getInt(KEY_PROGRESS))
        }else if(bundle.containsKey(KEY_STATUS)){
            handleStatusMessage(bundle.getString(KEY_STATUS))
        }
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        if(isSuccess)
            notifyStateListeners(STATUS_DOWNLOAD_COMPLETED)
    }


    private fun handleTotalDownloadMessage(totalDownload: Long) {
        updateListener?.onCalculateTotalDownload(totalDownload)
    }

    private fun handleProgressMessage(progress: Int) {
        updateListener?.onUpdateProgress(progress)

        createBuilderNoti()
        updateNoti(progress)
    }

    private fun createBuilderNoti() {
        if(builder!=null) return
        if(notiBundle == null) {
            // TODO: generate error
            return
        }
        val context = notiBundle.context
        if(context == null) {
            // TODO: generate error
            return
        }

        val intent = Intent(NotiUtil.ACTION_NOTI_DOWNLOAD_STOP)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)

        builder = NotiUtil.createBuilder(context, notiBundle.channelID, notiBundle.smallIcon, notiBundle.contentTitle, R.drawable.ic_pause_black_24dp, pendingIntent)
                .setWhen(Calendar.getInstance().timeInMillis)
    }

    private var totalStrDisplay:String?=null
    private val NOTIFY_ID = 1

    private fun updateNoti(progress: Int) {
        builder?.also { builder ->
            var contentText = ""

            if(progress == 100){
                contentText = notiBundle?.context?.getString(R.string.text_download_complete)?:"Download complete"
            }else{
                contentText = String.format("%d %% of %s", progress, totalStrDisplay)
            }
            builder.setContentText(contentText)

            val progressMax = if(progress == 100) 0 else 100
            val progressCur = if(progress == 100) 0 else progress

            val noti = builder.setProgress(progressMax, progressCur, false)
                    .build()
            noti.flags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_ONLY_ALERT_ONCE
            notiManager?.notify(NOTIFY_ID, noti)
        }
    }

    private fun handleStatusMessage(status: String?) {
        if(status==null) return
        notifyStateListeners(status)
    }

    private var isSuccess = false

    private var weakUpdateDownloadValueListener: WeakReference<OnDownloadProgressValueListener>?=null

    private val weakStateListeners = ArrayList<WeakReference<OnChangeStateListener>>()

    fun addStateListener(listener:OnChangeStateListener){
        val weakListener = WeakReference(listener)
        weakStateListeners.add(weakListener)
    }

    private fun notifyStateListeners(status:String){
        for (weakListener in weakStateListeners){
            weakListener.get()?.onChangeState(status)
        }
    }

    var updateListener: OnDownloadProgressValueListener?
        get() = weakUpdateDownloadValueListener?.get()
        set(value) {
            value?.also {
                weakUpdateDownloadValueListener = WeakReference(it)
            }
        }

    interface OnDownloadProgressValueListener{
        fun onCalculateTotalDownload(total:Long)
        fun onUpdateProgress(progress:Int)
    }

    interface OnChangeStateListener{
        fun onChangeState(status:String)
    }

    private val KEY_PROGRESS = "PROGRESS"
    private val KEY_TOTAL = "TOTAL LENGTH"
    private val KEY_STATUS = "STATUS"
}