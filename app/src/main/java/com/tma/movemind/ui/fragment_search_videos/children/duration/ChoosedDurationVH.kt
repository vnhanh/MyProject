package com.tma.movemind.ui.fragment_search_videos.children.duration

import android.support.v7.widget.RecyclerView
import android.view.View
import com.tma.movemind.base.dagger.ui.BaseVH
import kotlinx.android.synthetic.main.viewholder_search_videos_normal.view.*
import com.tma.movemind.base.models.Duration

class ChoosedDurationVH(view:View, listener: ChoosedDurationsAdapter.OnItemClickListener?) : BaseVH<Duration>(view) {
    init {
        view.setOnClickListener {
            listener?.onClick(data)
        }
    }

    override fun bind(_item: Duration){
        super.bind(_item)

        val text = String.format("%s %s", data?.timeNumber.toString(), ChoosedDurationsAdapter.subText)
        itemView.tv_value.text = text
    }
}