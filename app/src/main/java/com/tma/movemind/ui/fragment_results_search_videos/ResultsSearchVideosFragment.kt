package com.tma.movemind.ui.fragment_results_search_videos


import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tma.movemind.R
import com.tma.movemind.base.customs.ItemDecorationGridHorizontal
import com.tma.movemind.base.dagger.ui.BaseFragment
import com.tma.movemind.ui.fragment_results_search_videos.mvp.IResultsSearchVideosContract
import com.tma.movemind.ui.fragment_results_search_videos.recyclerview.ResultSearchedVideo
import com.tma.movemind.ui.fragment_results_search_videos.recyclerview.ResultSearchedVideosAdapter
import com.tma.movemind.ui.fragment_results_search_videos.recyclerview.ResultSearchedVideosGenerator
import kotlinx.android.synthetic.main.fragment_results_search_videos.*
import vn.com.tma.custom_exoplayer.Constant.TAG_LOG
import javax.inject.Inject


class ResultsSearchVideosFragment : BaseFragment(), IResultsSearchVideosContract.View {

    @Inject lateinit var presenter:IResultsSearchVideosContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_results_search_videos, container, false)
    }

    override fun onStart() {
        super.onStart()

        setupUI()

    }

    private fun setupUI() {
        context?.also { context ->
            val adapter = ResultSearchedVideosAdapter(context, object : ResultSearchedVideosAdapter.OnItemClickedListener{
                override fun onClick(item: ResultSearchedVideo) {

                }

            })
            adapter.list = ResultSearchedVideosGenerator.getList()
            recyclerview_videos_results_searched.adapter = adapter
            val rowNumber = 3
            recyclerview_videos_results_searched.layoutManager = GridLayoutManager(context, rowNumber, GridLayoutManager.HORIZONTAL, false)
            recyclerview_videos_results_searched.setHasFixedSize(true)

            val itemDecoration = ItemDecorationGridHorizontal
                    .Builder(context)
                    .dividerDrawableResID(R.drawable.bg_gray_rectangle_divider)
                    .dividerSizeResID(R.dimen.size_divider)
                    .spanNumber(rowNumber)
                    .build()

            recyclerview_videos_results_searched.addItemDecoration(itemDecoration)
        }
    }

    /**
     * Implementing IResultsSearchVideosContract.View
     */

    override fun getViewContext(): Context? = context

    /**
     * ----------------------------------------------
     */


    companion object {
        val TAG = "ResultsSearchVideosFragment"
        private var INSTANCE: ResultsSearchVideosFragment?=null

        fun getInstance(): ResultsSearchVideosFragment {
            if(INSTANCE == null){
                INSTANCE = ResultsSearchVideosFragment()
            }

            return INSTANCE!!
        }
    }
}
