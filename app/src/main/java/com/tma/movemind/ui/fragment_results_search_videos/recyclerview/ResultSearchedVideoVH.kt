package com.tma.movemind.ui.fragment_results_search_videos.recyclerview

import android.support.v4.content.ContextCompat
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tma.movemind.R
import com.tma.movemind.base.dagger.ui.BaseVH
import com.tma.movemind.base.utils.StringUtil
import kotlinx.android.synthetic.main.viewholder_result_searched_video.view.*
import kotlinx.android.synthetic.main.viewholder_scheduler_event_practice.view.*

class ResultSearchedVideoVH(itemView: View, val requestOptions: RequestOptions) : BaseVH<ResultSearchedVideo>(itemView){
    private var isSelected = false

    init {
        itemView.setOnClickListener {
            // trig flag
            isSelected = !isSelected

            // show UI
            val colorResId:Int

            if(isSelected)
                colorResId = R.color.colorGraySelectedItem
            else{
                colorResId = R.color.colorWhite
            }

            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, colorResId))

            itemView.icon_checked.visibility = if(isSelected) View.VISIBLE else View.GONE
        }
    }

    override fun bind(_item:ResultSearchedVideo){
        super.bind(_item)

        data?.videoPreviewImage?.also {
            Glide.with(itemView.context).load(it).apply(requestOptions).into(itemView.img_preview_video)
        }

        itemView.tv_title_searched_video.text = data?.videoName ?: ""
        itemView.tv_instructor_name.text = data?.instructorName?:""
        itemView.tv_duration.text = StringUtil.convertDurationToString(data?.duration?:0)
        itemView.tv_collection_desc.text = data?.collectionName?:""
        itemView.tv_brand_name.text = data?.brandName?:""
    }
}