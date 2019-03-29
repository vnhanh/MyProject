package com.tma.movemind.ui.fragment_search_videos.children.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tma.movemind.R
import com.tma.movemind.base.dagger.ui.BaseClickableAdapter
import com.tma.movemind.base.models.GymCollection

class ChoosedCollectionsAdapter(listener:OnItemClickListener?=null)
    : BaseClickableAdapter<ChoosedCollectionVH, ChoosedCollectionsAdapter.OnItemClickListener, GymCollection>(listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoosedCollectionVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_search_videos_normal, parent, false)
        return ChoosedCollectionVH(view, weakListener?.get())
    }

    interface OnItemClickListener{
        fun onClick(value: GymCollection?)
    }
}