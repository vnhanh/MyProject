package vn.com.tma.vo_ngoc_hanh.network.notification

import android.content.Context
import android.support.annotation.DrawableRes
import java.lang.ref.WeakReference

class NotiSetupBundle (context:Context, val channelID:String, val notiName:String, val notiDescription:String,
                       @DrawableRes val smallIcon:Int, val contentTitle:String, val contentText:String,
                       val progressMax:Int=-1, val progressCurrent:Int=-1){

    private val weakContext:WeakReference<Context>
    val context:Context?
    get() = weakContext.get()

    init {
        weakContext = WeakReference(context)
    }
}