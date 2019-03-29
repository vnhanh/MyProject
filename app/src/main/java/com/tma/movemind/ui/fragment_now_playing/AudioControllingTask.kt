package com.tma.movemind.ui.fragment_now_playing

import android.content.Context
import android.media.AudioManager
import android.os.AsyncTask
import android.util.Log
import com.tma.movemind.base.utils.Constant
import vn.com.tma.custom_exoplayer.PlayerManager
import java.lang.ref.WeakReference

class AudioControllingTask() : AsyncTask<Int, Void?, Void>() {
    private var weakContext:WeakReference<Context>?=null
    private var weakPlayerManager:WeakReference<PlayerManager>?=null

    var context:Context?
    get() = weakContext?.get()
    set(value) {
        value?.also {
            weakContext = WeakReference(it)
        }
    }

    var playerManager:PlayerManager?
    get() = weakPlayerManager?.get()
    set(value) {
        value?.also {
            weakPlayerManager = WeakReference(it)
        }
    }
    override fun doInBackground(vararg values: Int?): Void? {
        (context?.getSystemService(Context.AUDIO_SERVICE) as AudioManager).also { audioManager ->
            playerManager?.player?.audioStreamType?.also {
                values[0]?.also { value ->
                    Log.d(Constant.TAG_LOG, "task | audioStreamType: ${it} | volume value: ${value}")

                    audioManager.setStreamVolume(it, value/5, 0)
                }
            }
        }

        return null
    }
}