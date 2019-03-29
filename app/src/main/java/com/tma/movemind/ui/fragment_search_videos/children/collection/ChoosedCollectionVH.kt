package com.tma.movemind.ui.fragment_search_videos.children.collection

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.tma.movemind.base.dagger.ui.BaseVH
import kotlinx.android.synthetic.main.viewholder_search_videos_normal.view.*
import com.tma.movemind.base.models.GymCollection
import com.tma.movemind.base.utils.Constant.TAG_LOG

class ChoosedCollectionVH(view: View, listener: ChoosedCollectionsAdapter.OnItemClickListener?) : BaseVH<GymCollection>(view) {


    init {
        view.setOnClickListener {
            listener?.onClick(data)
        }
    }

    override fun bind(_item: GymCollection){
        super.bind(_item)
        itemView.tv_value.text = data?.name?:""
    }
}