package com.tma.movemind.ui.fragment_search_videos.children.brand

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tma.movemind.R
import com.tma.movemind.base.dagger.ui.BaseClickableAdapter
import com.tma.movemind.base.models.Brand

class ChoosedBrandsAdapter(listener:OnItemClickListener?) : BaseClickableAdapter<ChoosedBrandVH, ChoosedBrandsAdapter.OnItemClickListener, Brand>(listener) {

    override fun onBindViewHolder(holder: ChoosedBrandVH, position: Int) {
        holder.bind(list.get(position), position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoosedBrandVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_search_videos_one_or_two_rows, parent, false)
        return ChoosedBrandVH(view, weakListener?.get())
    }

    interface OnItemClickListener{
        fun onClick(brand: Brand?)
    }
}