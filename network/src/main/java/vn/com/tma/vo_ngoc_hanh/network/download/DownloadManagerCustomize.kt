package vn.com.tma.vo_ngoc_hanh.network.download

import android.content.Context
import android.net.ConnectivityManager
import android.os.Environment
import android.util.Log
import java.io.File
import java.lang.Exception
import java.lang.ref.WeakReference

/**
 * This manager class contain a queue download data and manage download work
 * It download one by one until the queue is empty
 * When download task completed, its download data would be removed from the queue
 * If network interrupted while download task running, this manager would restart downloading once network reconnect
 * If download data invalid, they would be deleted and the manager start the next download task (if exist)
 */
class DownloadManagerCustomize(context: Context) : NetworkManager.OnChangeNetworkState, WriteResponseToFileTask.OnChangeStateListener {
    private val TAG = "LOG"

    private lateinit var weakContext:WeakReference<Context>

    init {
        weakContext = WeakReference(context)
    }

    val queue = ArrayList<DownloadBundle>()

    private var isPreNetworkConnected = false

    // flag represents whether DownloadManager started
    // use for network state listener
    private var isStarted = false

    private var downloadUsecase:DownloadUsecase ?= null

    /*
     * Listen network connection changed state
     */
    override fun onChangeState(isConnected: Boolean) {
        Log.d(TAG, "CusDownloadManager | onChangeState: ${isConnected}")

        if(isStarted && isPreNetworkConnected == isConnected)
            return

        if (isConnected){
            if(!isStarted){
                val message = DownloadTaskMessage(STATUS_CONNECTED)
                statusChangedListener?.onStatusChanged(message)
            }
            else{
                val message = DownloadTaskMessage(STATUS_RECONNECT)
                statusChangedListener?.onStatusChanged(message)
            }

            startDownloadTaskIfExist()
        }else{
            if(queue.size > 0){
                val data = queue[0]
                val exception = getException(data, ERROR_DISCONNECTED)
                errorListener?.onError(exception)
            }

            statusChangedListener?.onStatusChanged(DownloadTaskMessage(ERROR_DISCONNECTED))
        }

        isStarted = true

        isPreNetworkConnected = isConnected
    }

    private fun startDownloadTaskIfExist(){
        Log.d(TAG, "CusDownloadManager | startDownloadTaskIfExist | size of queue: ${queue.size}")
        if(queue.size == 0)
            return

        val firstDownloadTaskData = queue.get(0)
        startDownload(firstDownloadTaskData)
    }

    /*
     * Add new download data into queue
     * and start download task if it is only
     */
    fun startDownloadOrEnqueue(data: DownloadBundle){
        queue.add(data)
        Log.d(TAG, "\t\tstartDownloadOrEnqueue | size of queue: ${queue.size}")

        if(queue.size == 1){
            Log.d(TAG, "\t\tstartDownloadOrEnqueue | download")
            startDownload(data)
        }
    }

    fun pauseDownload() {
        downloadUsecase?.pause()
        queue.removeAt(0)
    }

    /**
     * call from @startDownloadOrEnqueue method
     */
    private fun startDownload(firstTaskData: DownloadBundle){
        Log.d(TAG, "\t\tstartDownload | file: ${firstTaskData.savedFolderPath}")

        // check data valid for starting download task
        if(!checkValidDataForStartDownload(firstTaskData)){
            Log.d(TAG, "\t\tstartDownload | checkValidDataForStartDownload: failed")
            // data is not valid then remove it and download next task if can
            //TODO: need to ask user remove to operate the next
            removeFirstAndStartNextDownload()
        }
        else{
            if(!checkIsNetworkConnected()){
                Log.d(TAG, "\t\tstartDownload | checkIsNetworkConnected: failed")
                // network disconnected
                val ex = getException(firstTaskData, ERROR_DISCONNECTED)
                errorListener?.onError(ex)
                return
            }

            Log.d(TAG, "\t\tstartDownload | run download task")
            val _fileName = DownloadManagerCustomize.getFilename(firstTaskData.downloadUrl)
            val savedFolder = File(Environment.getExternalStorageDirectory(), "/MoveMind/")
            val updateValueListener = firstTaskData.updateListener!!

            val stateListeners = ArrayList<WriteResponseToFileTask.OnChangeStateListener>()
            stateListeners.add(firstTaskData.changeStatesListener!!)
            stateListeners.add(this)

            downloadUsecase = DownloadUsecase(savedFolder.path, _fileName, firstTaskData.notiBundle).apply {
                startDownload(updateValueListener, stateListeners)
            }
        }
    }

    /**
     * Local function checks data valid before start download task
     * Return value: false -> not valid and revert
     */
    private fun checkValidDataForStartDownload(data: DownloadBundle) : Boolean{
        val url = data.downloadUrl

        if(url == null || "".equals(url)){
            val e = getException(data, ERROR_DOWNLOAD_URL_EMPTY)
            errorListener?.onError(e)
            return false
        }

        val savedPath = data.savedFolderPath
        if(savedPath == null){
            val e = getException(data, ERROR_SAVED_PATH_NULL)
            errorListener?.onError(e)
            return false
        }

        if(data.updateListener == null){
            // if this listener was null, DownloadManager would not controll download task
            val e = getException(data, ERROR_CHANGE_STATES_LISTENER_NULL)
            errorListener?.onError(e)
            return false
        }

        if(data.changeStatesListener == null){
            // if this listener was null, DownloadManager would not controll download task
            val e = getException(data, ERROR_CHANGE_STATES_LISTENER_NULL)
            errorListener?.onError(e)
            return false
        }

        return true
    }

    /**
     * DownloadTask.OnChangeStateListener
     */

    override fun onChangeState(status: String) {
        Log.d(TAG, "CusDownloadManager | onChangeState | state: ${status} | isConnected: ${isPreNetworkConnected}")

        when(status) {
            // interrupted by disconnected or wrong url or ...
            ERROR_DOWNLOAD_INTERRUPTED, ERROR_IO_EXCEPTION -> {
                weakContext.get()?.also { context ->

                    // network fine so change to the next task
                    if(checkIsNetworkConnected()){
                        errorListener?.onError(Exception(ERROR_DOWNLOAD_INTERRUPTED))
                        removeFirstAndStartNextDownload()
                    }
                }

            }

            // file downloaded comleted
            STATUS_FILE_ALREADY_DOWNLOADED, STATUS_DOWNLOAD_COMPLETED -> {

                downloadUsecase = null
                removeFirstAndStartNextDownload()
            }
        }
    }

    /**
     * End
     */

    // local function that checks whether network connection connected or not
    private fun checkIsNetworkConnected() : Boolean{
        weakContext.get()?.also { context ->
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val activeNetInfo = cm.activeNetworkInfo

            return activeNetInfo != null && activeNetInfo.isConnected
        }
        return false
    }

    private fun removeFirstAndStartNextDownload(){
        queue.removeAt(0)
        startDownloadTaskIfExist()
    }

    fun isDownloadTaskQueueEmpty() : Boolean = queue.size == 0

    fun deleteIdleTaskAtIndex(index:Int) : Boolean{
        if(index < 1) return false

        queue.removeAt(index)
        return true
    }

    private var weakErrorListener:WeakReference<OnErrorListener>?=null
    var errorListener: OnErrorListener?
        get() = weakErrorListener?.get()
        set(value) {
            value?.also {
                weakErrorListener = WeakReference(value)
            }
        }

    private var weakStatusChangedListener:WeakReference<OnStatusChangedListener>?=null
    var statusChangedListener: OnStatusChangedListener?
        get() = weakStatusChangedListener?.get()
        set(value) {
            value?.also {
                weakStatusChangedListener = WeakReference(value)
            }
        }

    /**
     *  Listener for listening errors users could get
     *  such as disconnected, interrupted by server, url etc.
      */
    interface OnErrorListener{
        fun onError(exception: Exception)
    }

    /**
     * Listener for listening status users could get
     * such as file downloading, file download completed
     */
    interface OnStatusChangedListener{
        fun onStatusChanged(message: DownloadTaskMessage)
    }

    // Create an DownloadTaskException exception containing detail information such as file name, url
    private fun getException(data: DownloadBundle, message:String): DownloadTaskException {
        val exception = DownloadTaskException(message)
        exception.fileName = getFilename(data.savedFolderPath)
        exception.downloadUrl = data.downloadUrl
        return exception
    }

    // Create an DownloadTaskMessage exception containing detail information such as file name, content of status
    private fun getMessageReturn(data: DownloadBundle, content:String) : DownloadTaskMessage {
        val message = DownloadTaskMessage(content)
        message.filename = data.fileName
        return message
    }

    /**
     * Message class for noticing user about what status is happening
     * such as network reconnect, downloading file, download completed etc.
     */
    class DownloadTaskMessage(val content:String) {
        var filename:String?=null
    }

    /**
     * Exception class for noticing user about what error has happened
     * such as network disconnected, download progress interrupted etc.
     */
    class DownloadTaskException(message:String) : Exception(message){
        var fileName:String?=null
        var downloadUrl:String?=null
    }


    companion object {
        val STATUS_START_DOWNLOAD = "STATUS START DOWNLOADING"
        val STATUS_OPENING_CONNECTION = "STATUS OPENING CONNECTION TO DOWNLOAD SERVER"
        val STATUS_CONNECTED = "NETWORK CONNECTED"
        val STATUS_DOWNLOADING = "STATUS DOWNLOADING"
        val STATUS_RECONNECT = "STATUS RECONNECT"
        val STATUS_FILE_ALREADY_DOWNLOADED = "STATUS FILE WAS ALREADY DOWNLOADED"
        val STATUS_DOWNLOAD_COMPLETED = "DOWNLOAD COMPLETED"

        val ERROR_DISCONNECTED = "ERROR NETWORK DISCONNECTED"
        val ERROR_DOWNLOAD_URL_EMPTY = "ERROR DOWNLOAD URL EMPTY"
        val ERROR_SAVED_PATH_NULL = "ERROR SAVED PATH IS NULL"
        val ERROR_IO_EXCEPTION = "ERROR ACCESSING FILE"
        val ERROR_DOWNLOAD_INTERRUPTED = "ERROR DOWNLOAD TASK WAS INTERRUPTED"
        val ERROR_UNKNOWN_FILE_NAME = "ERROR UNKNOWN FILE NAME"
        val ERROR_CHANGE_STATES_LISTENER_NULL = "ERROR CHANGE_STATES_LISTENER IS NULL"

        /*
         * Get file name from url string
         */
        fun getFilename(url:String?):String{
            if(url == null || "".equals(url))
                return ""

            val slashIndex = url.lastIndexOf("/")

            if(slashIndex < 0)
                return ""

            return url.substring(slashIndex+1, url.length)
        }

        /*
         * Remove all slash character ("/") from url string
         */
        fun filterOutUrl(url:String?):String{
            if(url == null || "".equals(url))
                return ""

            var slashIndex = url.lastIndexOf("/")
            while (slashIndex > -1 && url[slashIndex].equals("/")){
                slashIndex--
            }

            if(slashIndex<0)
                return ""

            return url.substring(0, slashIndex+1)
        }
    }
}