package com.tma.movemind.ui.fragment_search_videos.children.instructor

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tma.movemind.R
import com.tma.movemind.base.dagger.ui.BaseClickableAdapter
import com.tma.movemind.base.models.Instructor
import com.tma.movemind.base.models.Level

class ChoosedInstructorsAdapter(listener: OnItemClickListener?)
    : BaseClickableAdapter<ChoosedInstructorVH, ChoosedInstructorsAdapter.OnItemClickListener, Instructor>(listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoosedInstructorVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_search_video_instructor, parent, false)
        return ChoosedInstructorVH(view, weakListener?.get())
    }

    interface OnItemClickListener{
        fun onClickItem(instructor: Instructor)

        fun onClickShowInfo(instructor: Instructor)
    }
}