package vn.com.tma.vo_ngoc_hanh.network.download

import android.app.IntentService
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.com.tma.vo_ngoc_hanh.network.ApiUtil
import vn.com.tma.vo_ngoc_hanh.network.notification.NotiSetupBundle
import vn.com.tma.vo_ngoc_hanh.network.utils.NumberUtil
import java.io.*

class WriteResponseToFileService : IntentService("WriteResponseToFileService") {
    private var resultReceiver:ResultReceiver?=null

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "DownloadService | onHandleIntent")

        if(intent == null || !intent.hasExtra(EXTRA_DOWNLOAD)) return
        val isDownload = intent.getBooleanExtra(EXTRA_DOWNLOAD, false)

        if(!isDownload){
            stopTask()
        }else{
            resultReceiver = intent.getParcelableExtra(EXTRA_RESULT_RECEIVER)
            val savedFolderPath = intent.getStringExtra(EXTRA_SAVED_FOLDER_PATH)
            val fileName = intent.getStringExtra(EXTRA_FILE_NAME)
            val url = intent.getStringExtra(EXTRA_URL)

//            download(savedFolderPath, fileName)
        }
    }

    private fun stopTask() {
        stopSelf()
    }

    private val TAG = "LOG"
    private val DEFAULT_BUFFER = 4096

    private var isSuccess = false

    private var totalStrDisplay:String?=null
    private val NOTIFY_ID = 1


    companion object {
        val EXTRA_DOWNLOAD = "DOWNLOAD SERVICE"
        val EXTRA_RESULT_RECEIVER = "RESULT RECEIVER"
        val EXTRA_SAVED_FOLDER_PATH = "SAVED FOLDER PATH"
        val EXTRA_FILE_NAME = "FILE NAME"
        val EXTRA_URL = "DOWNLOAD URL"

        val RESULT_CODE_OK = 0
        val RESULT_CODE_ERROR = -1
        val KEY_PROGRESS = "PROGRESS"
        val KEY_TOTAL_DOWNLOAD = "TOTAL DOWNLOAD"
        val KEY_STATUS = "STATUS"
    }

    private fun notiProgressUI(progress:Int){
        val bundle = Bundle()
        bundle.putInt(KEY_PROGRESS, progress)
        resultReceiver?.send(RESULT_CODE_OK, bundle)
    }

    private fun notiTotalDownloadUI(total:Long){
        val bundle = Bundle()
        bundle.putLong(KEY_TOTAL_DOWNLOAD, total)
        resultReceiver?.send(RESULT_CODE_OK, bundle)
    }

    private fun notiStatusProgressUI(status:String){
        val bundle = Bundle()
        bundle.putString(KEY_STATUS, status)
        resultReceiver?.send(RESULT_CODE_OK, bundle)
    }

    private fun notiErrorUI(error:String){
        val bundle = Bundle()
        bundle.putString(KEY_STATUS, error)
        resultReceiver?.send(RESULT_CODE_ERROR, bundle)
    }

    private var task:WriteResponseToFileTask?=null

    private fun write(savedFolderPath:String, fileName:String){
        try {
            synchronized(savedFile, {
                if(!savedFile.exists())
                    savedFile.createNewFile()

                var inputStream: InputStream? = null
                var outputStream: OutputStream? = null

                try {
                    val fileReader = ByteArray(DEFAULT_BUFFER)

                    val downloadLength = responseBody.contentLength()
                    val totalLength = downloadedLength + downloadLength

                    if(notiBundle!=null){
                        totalStrDisplay = NumberUtil.calculateCapacityUnit(totalLength)
                    }

                    notiTotalDownloadUI(totalLength)

                    var progress:Int = (downloadedLength * 100 / totalLength).toInt()
                    var preProgress = progress

                    notiProgressUI(progress)

                    if(progress == 100) {
                        notiStatusProgressUI(DownloadManagerCustomize.STATUS_FILE_ALREADY_DOWNLOADED)
                        return
                    }

                    Log.d(TAG, "WriteResponseToFileService | fileSize: ${totalLength}")

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
                            Log.d(TAG, "WriteResponseToFileService | progress: ${progress} | preProgress: ${preProgress}")
                            preProgress = progress

                            notiProgressUI(progress)
                        }

                        if(progress == 100) isSuccess = true
                    }

                    outputStream.flush()

                } catch (e: IOException) {
                    Log.d(TAG, "WriteResponseToFileService | error: ${e}")
                    notiErrorUI(DownloadManagerCustomize.ERROR_DOWNLOAD_INTERRUPTED)
                } finally {
                    inputStream?.apply { close() }
                    outputStream?.apply { close() }
                }
            })
        } catch (e: IOException) {
            Log.d(TAG, "WriteResponseToFileService | error: ${e}")
            notiErrorUI(DownloadManagerCustomize.ERROR_IO_EXCEPTION)
        }
    }

    fun download(savedFolderPath:String, fileName:String, notiBundle: NotiSetupBundle?=null){
        val folder = File(savedFolderPath)
        if(!folder.exists()){
            folder.mkdirs()
        }

        val savedFile = File(folder, fileName)
        if(!savedFile.exists())
            savedFile.createNewFile()
        val downloadedLength = savedFile.length()
        val downloadRangeHeader = String.format("bytes=%d-", downloadedLength)

        val downloadService = ApiUtil.getDownloadService("https://github.com/")
        val call = downloadService.downloadFileWithFixedUrl("/git-for-windows/git/releases/download/v2.21.0.windows.1/Git-2.21.0-64-bit.exe", downloadRangeHeader)
        call.enqueue(object: Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d(TAG, "onFailure")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d(TAG, "onResponse | ${response.body()}")

                val responseBody = response.body()
                if(responseBody == null) return

            }

        })
    }
}