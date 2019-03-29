package vn.com.tma.custom_exoplayer

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

/**
 * Setups sorts of ExoPlayer
 */
object ExoPlayerUtil {
    fun getDefaultSimpleExoPlayerInstance(context:Context) : SimpleExoPlayer {
        val bandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

        // 2. Create the player
        return ExoPlayerFactory.newSimpleInstance(context, trackSelector)
    }

    fun initPlayer(context: Context, appName:String, mediaUrl:String?, playWhenReady:Boolean = false) : SimpleExoPlayer?{
        var player:SimpleExoPlayer?=null

        mediaUrl?.also {
            player = getDefaultSimpleExoPlayerInstance(context).also { player ->
                val dataSourceFactory = DefaultDataSourceFactory(
                        context, Util.getUserAgent(context, appName))
                val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(mediaUrl))
                player.prepare(mediaSource)
                player.playWhenReady = playWhenReady
            }
        }

        return player
    }
}