package com.tma.movemind.ui.fragment_search_videos.children.instructor

import android.support.v7.widget.RecyclerView
import android.view.View
import com.tma.movemind.base.dagger.ui.BaseVH
import kotlinx.android.synthetic.main.viewholder_search_video_instructor.view.*
import com.tma.movemind.base.models.Instructor


class ChoosedInstructorVH(view: View, listener: ChoosedInstructorsAdapter.OnItemClickListener?) : BaseVH<Instructor>(view) {

    init {
        view.setOnClickListener {
            data?.also {
                listener?.onClickItem(it)
            }
        }
        view.btn_show_info.setOnClickListener {
            data?.also {
                listener?.onClickShowInfo(it)
            }
        }
    }

    override fun bind(_item: Instructor){
        super.bind(_item)
        itemView.tv_instructor_name.text = data?.fullName?:""
    }
}