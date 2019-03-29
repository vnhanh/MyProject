package com.tma.movemind.ui.fragment_helper


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tma.movemind.R
import com.tma.movemind.base.utils.Util
import com.tma.movemind.ui.fragment_control.ControlFragment
import com.tma.movemind.ui.fragment_help_me_choose.HelpMeChooseFragment
import com.tma.movemind.ui.fragment_search_videos.SearchVideosFragment
import kotlinx.android.synthetic.main.fragment_helper.*


class HelperFragment : Fragment() {
    companion object {
        val TAG  = "HelperFragment"
        private var INSTANCE:HelperFragment ?= null

        fun getInstance() : HelperFragment {
            if(INSTANCE == null){
                INSTANCE = HelperFragment()
            }

            return INSTANCE!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_helper, container, false)
    }

    override fun onStart() {
        super.onStart()

        setupUI()
    }

    private fun setupUI() {
        btn_skip_search.setOnClickListener {
            val manager = activity?.supportFragmentManager
            val newTag = SearchVideosFragment.TAG
            val newFragment = ControlFragment.getInstance(ControlFragment.MODE_SEARCH)
            Util.addOrShowFragment(manager, newFragment, newTag, TAG, R.id.frame_layout_home, true)
        }

        btn_help_me_choose.setOnClickListener {
            activity?.supportFragmentManager?.also { manager->
                val newTag = HelpMeChooseFragment.TAG
                val newFragment = ControlFragment.getInstance(ControlFragment.MODE_HELP_CHOOSE)
                Util.addOrShowFragment(manager, newFragment, newTag, TAG, R.id.frame_layout_home, true)
            }
        }
    }
}
