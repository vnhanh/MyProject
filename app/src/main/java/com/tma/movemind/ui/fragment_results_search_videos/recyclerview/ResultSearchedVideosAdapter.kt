package com.tma.movemind.ui.fragment_results_search_videos.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.request.RequestOptions
import com.tma.movemind.R
import com.tma.movemind.base.dagger.ui.BaseClickableAdapter
import com.tma.movemind.base.utils.GlideUtil

class ResultSearchedVideosAdapter(context: Context, listener:OnItemClickedListener)
    : BaseClickableAdapter<ResultSearchedVideoVH, ResultSearchedVideosAdapter.OnItemClickedListener, ResultSearchedVideo>(listener) {
    val requestOptions:RequestOptions

    init {
        val imageSize = context.resources.getDimensionPixelSize(R.dimen.size_image_preview_searched_video)
        requestOptions = GlideUtil.getDefaultRequestOptions(imageSize, imageSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultSearchedVideoVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_result_searched_video, parent, false)
        return ResultSearchedVideoVH(view, requestOptions)
    }

    interface OnItemClickedListener{
        fun onClick(item:ResultSearchedVideo)
    }
}