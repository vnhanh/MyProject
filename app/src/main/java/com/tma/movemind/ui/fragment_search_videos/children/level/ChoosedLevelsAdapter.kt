package com.tma.movemind.ui.fragment_search_videos.children.level

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tma.movemind.R
import com.tma.movemind.base.dagger.ui.BaseClickableAdapter
import com.tma.movemind.base.models.Level


class ChoosedLevelsAdapter(listener: OnItemClickListener?) : BaseClickableAdapter<ChoosedLevelVH, ChoosedLevelsAdapter.OnItemClickListener, Level>(listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoosedLevelVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_search_videos_normal, parent, false)
        return ChoosedLevelVH(view, weakListener?.get())
    }

    interface OnItemClickListener{
        fun onClick(level: Level?)
    }
}