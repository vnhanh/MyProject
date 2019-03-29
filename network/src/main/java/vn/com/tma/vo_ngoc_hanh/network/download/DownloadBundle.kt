package vn.com.tma.vo_ngoc_hanh.network.download

import vn.com.tma.vo_ngoc_hanh.network.notification.NotiSetupBundle
import java.lang.ref.WeakReference

/**
 *
 */
class DownloadBundle (var downloadUrl:String?=null,
                      var savedFolderPath:String?=null){
    var notiBundle:NotiSetupBundle?=null

    var fileName:String?=null

    private var weakUpdateListener:WeakReference<WriteResponseToFileTask.OnDownloadProgressValueListener>?=null
    private var weakChangeStatesListener:WeakReference<WriteResponseToFileTask.OnChangeStateListener>?=null

    var updateListener: WriteResponseToFileTask.OnDownloadProgressValueListener?
        get() = weakUpdateListener?.get()
        set(value){
            value?.also {
                weakUpdateListener = WeakReference(it)
            }
        }

    // not set this listener from external module
    // this listener would assigned for DownloadManagerCustomize
    var changeStatesListener: WriteResponseToFileTask.OnChangeStateListener?
        get() = weakChangeStatesListener?.get()
        set(value) {
            value?.also {
                weakChangeStatesListener = WeakReference(it)
            }
        }
}