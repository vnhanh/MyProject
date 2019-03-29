package com.tma.movemind.ui.fragment_search_videos.children.level

import android.view.View
import com.tma.movemind.base.dagger.ui.BaseVH
import kotlinx.android.synthetic.main.viewholder_search_videos_normal.view.*
import com.tma.movemind.base.models.Level

class ChoosedLevelVH(view: View, listener: ChoosedLevelsAdapter.OnItemClickListener?) : BaseVH<Level>(view) {

    init {
        view.setOnClickListener {
            listener?.onClick(data)
        }
    }

    override fun bind(_item: Level){
        super.bind(_item)
        itemView.tv_value.text = data?.name?:""
    }
}