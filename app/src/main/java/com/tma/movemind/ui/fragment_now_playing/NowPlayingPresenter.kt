package com.tma.movemind.ui.fragment_now_playing

import android.util.Log
import com.google.android.exoplayer2.*
import com.tma.movemind.base.utils.Constant.TAG_LOG
import vn.com.tma.custom_exoplayer.PlayerManager

class NowPlayingPresenter(var playManager: PlayerManager) : INowPlayingConstract.Presenter {

    override fun onInt() {
        if(!playManager.isPlayerInit)
            initPlayer()
    }

    override fun onRelease() {
        playManager.onRelease()
    }

    override fun onChangeVolume(progress: Int, p3: Boolean) {
        view?.getViewContext()?.also {context ->
            val value = progress * 1.0f / 100
            playManager.player?.volume = value
        }
    }

    private var view:INowPlayingConstract.View?=null
    private var playbackState:Int = Player.STATE_IDLE

    private fun initPlayer(){
        val mediaUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
        playManager.onInit(::handlePlaybackState, mediaUrl, false)
    }

    override fun onAttach(view: INowPlayingConstract.View) {
        this.view = view

        view.setupPlayerView(player = playManager.player)
        showUIOnPlaybackState()

        val volume = playManager.player?.volume?:0f
        view.setupVolumeSeekbar(volume)
    }

    override fun onDetach() {
        view = null
        playManager.pausePlayer() // pause if player inited
    }

    private fun handlePlaybackState(playbackState:Int){
        this.playbackState = playbackState
        showUIOnPlaybackState()
    }

    private fun showUIOnPlaybackState(){
        when (playbackState) {

            Player.STATE_BUFFERING -> {
                Log.d(TAG_LOG, this.javaClass.simpleName + " | playback state: buffering")
                view?.apply {
                    showProgress()
                }
            }
            Player.STATE_ENDED -> {
                Log.d(TAG_LOG, this.javaClass.simpleName + " | playback state: ended")
            }
            Player.STATE_IDLE -> {
                Log.d(TAG_LOG, this.javaClass.simpleName + " | playback state: idle")
                view?.apply {
                    onPreDisplayVideo()
                    showProgress()
                }
            }
            Player.STATE_READY -> {
                Log.d(TAG_LOG, this.javaClass.simpleName + " | playback state: ready | get play when ready: ${playManager.player?.playWhenReady}")
                view?.apply {
                    onDisplayVideo()
                    hideProgress()
                }
            }
            else -> {
                Log.d(TAG_LOG, this.javaClass.simpleName + " | playback state: unknown")
            }
        }
    }
}