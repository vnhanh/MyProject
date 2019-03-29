package com.tma.movemind.ui.fragment_now_playing


import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

import com.tma.movemind.R
import com.tma.movemind.base.dagger.ui.BaseFragment
import com.tma.movemind.base.utils.Constant.TAG_LOG
import com.tma.movemind.base.utils.Util
import com.tma.movemind.ui.fragment_control.ControlFragment
import com.tma.movemind.ui.fragment_time_table.TimeTableFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_now_playing.*
import javax.inject.Inject


class NowPlayingFragment : BaseFragment(), INowPlayingConstract.View {

    @Inject lateinit var presenter: INowPlayingConstract.Presenter

    // flags
    private var isInitedVolumeSeekbar = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onStart() {
        super.onStart()

        setupUI()
        if(Util.getSDKVersion() > 23){
            presenter.onInt()
        }
    }

    override fun onResume() {
        super.onResume()
        if(Util.getSDKVersion() <= 23 ){
            presenter.onInt()
        }
        presenter.onAttach(this)
    }

    override fun onPause() {
        presenter.onDetach()
        if(Util.getSDKVersion() <= 23){
            presenter.onRelease()
        }
        super.onPause()
    }

    override fun onDestroy() {
        if(Util.getSDKVersion() > 23){
            presenter.onRelease()
        }
        super.onDestroy()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(hidden){
            presenter.onDetach()
        }else{
            presenter.onAttach(this)
        }
    }

    private fun setupUI() {
        view?.setOnClickListener {
            hideVolumeSeekbarIfShown()
        }

        btn_view_time_table.setOnClickListener { _ ->
            activity?.also { activity ->
                val currTag = TAG
                val newFragment = ControlFragment.getInstance(ControlFragment.SHOW_SCHEDULERS)
                val newTag = TimeTableFragment.TAG
                Util.addOrShowFragment(activity.supportFragmentManager, newFragment, newTag, currTag, R.id.frame_layout_home)
            }
        }

        btn_volume.setOnClickListener {
            if(seekbar_volume.visibility != View.GONE)
                seekbar_volume.visibility = View.GONE
            else
                seekbar_volume.visibility = View.VISIBLE
        }

        seekbar_volume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, p3: Boolean) {
                if(!isInitedVolumeSeekbar){
                    isInitedVolumeSeekbar = true
                    return
                }
                presenter.onChangeVolume(progress, p3)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
    }

    private fun hideVolumeSeekbarIfShown(){
        if(seekbar_volume.visibility != View.GONE)
            seekbar_volume.visibility = View.GONE
    }


    /**
     *
     */

    override fun onPreDisplayVideo() {
        if(thumbnail_now_playing.visibility != View.VISIBLE)
            thumbnail_now_playing.visibility = View.VISIBLE

        if(video_player_now_playing.visibility != View.INVISIBLE)
            video_player_now_playing.visibility = View.INVISIBLE
    }

    override fun onDisplayVideo() {
        if(thumbnail_now_playing.visibility != View.GONE)
            thumbnail_now_playing.visibility = View.GONE

        if(video_player_now_playing.visibility != View.VISIBLE)
            video_player_now_playing.visibility = View.VISIBLE
    }

    override fun showProgress() {
        if(prg_bar_video_now_playing.visibility != View.VISIBLE)
            prg_bar_video_now_playing.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        prg_bar_video_now_playing.visibility = View.GONE
    }

    override fun setupPlayerView(player: SimpleExoPlayer?) {
        if(video_player_now_playing.player != null) return
        player?.also {
            video_player_now_playing.player = it
        }
    }

    override fun setupVolumeSeekbar(volume: Float) {
        seekbar_volume.progress = (volume * 100).toInt()
    }

    /**
     * -------------
     */

    companion object {
        val TAG = "NowPlayingFragment"
        private var INSTANCE:NowPlayingFragment?=null

        fun getInstance() : NowPlayingFragment {
            if(INSTANCE == null){
                INSTANCE = NowPlayingFragment()
            }

            return INSTANCE!!
        }
    }

}
