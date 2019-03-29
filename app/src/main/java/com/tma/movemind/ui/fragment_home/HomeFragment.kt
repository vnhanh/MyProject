package com.tma.movemind.ui.fragment_home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tma.movemind.R
import com.tma.movemind.base.utils.Util
import com.tma.movemind.ui.fragment_helper.HelperFragment
import com.tma.movemind.ui.fragment_now_playing.NowPlayingFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    companion object {
        val TAG = "HomeFragment"
        private var INSTANCE:HomeFragment?=null

        fun getInstance() : HomeFragment {
            if(INSTANCE == null){
                INSTANCE = HomeFragment()
            }

            return INSTANCE!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()

        setupUI()
    }

    private fun setupUI() {
        btn_get_started_schedulers.setOnClickListener {
            val newTag = NowPlayingFragment.TAG
            val currentTag = TAG
            val newFragment = NowPlayingFragment.getInstance()

            Util.addOrShowFragment(activity?.supportFragmentManager, newFragment, newTag, currentTag, R.id.frame_layout_home)
        }

        btn_get_started_no_schedulers.setOnClickListener {
            val newTag = HelperFragment.TAG
            val currentTag = TAG
            val newFragment = HelperFragment.getInstance()
            Util.addOrShowFragment(activity?.supportFragmentManager, newFragment, newTag, currentTag, R.id.frame_layout_home)
        }
    }
}