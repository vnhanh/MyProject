package vn.com.tma.vo_ngoc_hanh.network.utils

import android.app.PendingIntent
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.app.NotificationCompat

object NotiUtil {
    val ACTION_NOTI_DOWNLOAD_STOP = "vn.com.tma.vo_ngoc_hanh.network.download.stop"

    fun createBuilder(context: Context, channelID:String, @DrawableRes smallIcon:Int, contentTitle:String) : NotificationCompat.Builder
            = NotificationCompat.Builder(context, channelID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(smallIcon)
            .setContentTitle(contentTitle)
            .setOngoing(true)
            .setAutoCancel(true)

    fun createBuilder(context: Context, channelID:String, @DrawableRes smallIcon:Int, contentTitle:String,
                      @DrawableRes actionIconRes:Int, actionPendingIntent:PendingIntent) : NotificationCompat.Builder

            = NotificationCompat.Builder(context, channelID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(smallIcon)
            .setContentTitle(contentTitle)
            .addAction(actionIconRes, "Action Title", actionPendingIntent)
            .setOngoing(true)
            .setAutoCancel(true)
}