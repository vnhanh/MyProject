package com.tma.movemind.ui.fragment_search_videos.children.duration

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tma.movemind.R
import com.tma.movemind.base.dagger.ui.BaseClickableAdapter
import com.tma.movemind.base.models.Duration

class ChoosedDurationsAdapter(listener:OnItemClickListener?)
    : BaseClickableAdapter<ChoosedDurationVH, ChoosedDurationsAdapter.OnItemClickListener, Duration>(listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoosedDurationVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_search_videos_normal, parent, false)

        if(subText.equals("")){
            val context = parent.context
            subText = context.getString(R.string.minutes)
        }

        return ChoosedDurationVH(view, weakListener?.get())
    }

    interface OnItemClickListener{
        fun onClick(value: Duration?)
    }

    companion object {
        var subText:String = ""
    }
}