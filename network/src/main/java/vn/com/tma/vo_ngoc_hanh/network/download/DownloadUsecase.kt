package vn.com.tma.vo_ngoc_hanh.network.download

import android.os.AsyncTask
import android.util.Log
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import vn.com.tma.vo_ngoc_hanh.network.ApiUtil
import vn.com.tma.vo_ngoc_hanh.network.notification.NotiSetupBundle
import java.io.*
import java.lang.Exception


class DownloadUsecase(val savedFolderPath:String, val fileName:String, var notiBundle:NotiSetupBundle?=null) {
    private val TAG = "LOG"
    private var isAllowDownload = true
    private var task:WriteResponseToFileTask?=null

    fun startDownload(updateListener:WriteResponseToFileTask.OnDownloadProgressValueListener, changeStateListeners: ArrayList<WriteResponseToFileTask.OnChangeStateListener>){
        isAllowDownload = true

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

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponseBody>{
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        Log.d(TAG, "startDownload | onSubscribe")
                    }

                    override fun onNext(body: ResponseBody) {
                        Log.d(TAG, "startDownload | onNext | t: ${body} | isAllowDownload: ${isAllowDownload}")
                        if(isAllowDownload){
                            task = WriteResponseToFileTask(savedFolderPath, fileName, notiBundle).also {
                                it.updateListener = updateListener
                                for(listener in changeStateListeners){
                                    it.addStateListener(listener)
                                }
                                it.execute(body)
                            }
                        }else{
                            Thread{
                                Runnable {
                                    body.close()
                                }
                            }.run()
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "startDownload | Callback<ResponseBody> | error: ${e}")
                    }

                })
    }

    fun pause() {
        Log.d(TAG, "DownloadUsecase | pause | task: ${task}")
        isAllowDownload = false
        task?.cancel(true)
    }
}