package com.tma.movemind.ui.fragment_now_playing

import android.content.Context
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.tma.movemind.base.dagger.ui.ILifeCycle
import com.tma.movemind.base.dagger.ui.IProgressTitleView
import com.tma.movemind.base.dagger.ui.IProgressView

interface INowPlayingConstract {
    interface View : IProgressView, ILifeCycle.View{
        fun onPreDisplayVideo()
        fun onDisplayVideo()
        fun setupPlayerView(player: SimpleExoPlayer?)
        fun setupVolumeSeekbar(volume: Float)
    }

    interface Presenter : ILifeCycle.Presenter<View>{
        fun onInt()
        fun onRelease()
        fun onChangeVolume(progress: Int, p3: Boolean)
    }
}