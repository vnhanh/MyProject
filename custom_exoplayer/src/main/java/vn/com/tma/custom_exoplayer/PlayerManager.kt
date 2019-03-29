package vn.com.tma.custom_exoplayer

import android.content.Context
import android.support.annotation.StringRes
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.video.VideoListener
import java.lang.ref.WeakReference

class PlayerManager(context: Context) {
    private var weakContext: WeakReference<Context>

    var isPlayerInit = false
    get() = player != null

    var player: SimpleExoPlayer?=null
    var eventListener: Player.EventListener?=null
    private var lastContentPosition = 0L

    init {
        weakContext = WeakReference(context)
    }

    fun onInit(playbackStateHandler: (playbackState:Int) -> Unit, url:String, isPlayWhenReady:Boolean = false) : SimpleExoPlayer?{
        val context = getContext() ?: return null
        val appName = getString(R.string.app_name) ?: return null

        player = ExoPlayerUtil.initPlayer(context, appName, url, isPlayWhenReady)?.also {
            eventListener = ExoPlayerListenerInstaller.installHandlerPlaybackState(playbackStateHandler)
            it.addListener(eventListener)
        }
        return player
    }

    fun onRelease(){
        player?.also {
            lastContentPosition = it.contentPosition
            it.removeListener(eventListener)
            it.stop()
            it.release()
        }
        eventListener = null
        player = null
    }

    fun pausePlayer() {
        player?.playWhenReady = false
        lastContentPosition = player?.currentPosition?:0
    }

    fun playContinue() {
        player?.seekTo(lastContentPosition)
        player?.playWhenReady = true
    }

    private fun getContext() : Context? = weakContext.get()
    private fun getString(@StringRes id:Int) : String? = getContext()?.getString(id)
}