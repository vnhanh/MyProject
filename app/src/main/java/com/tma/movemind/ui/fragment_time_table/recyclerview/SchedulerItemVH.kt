package com.tma.movemind.ui.fragment_time_table.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.viewholder_scheduler_event_practice.view.*
import java.lang.ref.WeakReference

class SchedulerItemVH(view: View, weakListener: WeakReference<SchedulerAdapter.OnClickItemListener>?) : RecyclerView.ViewHolder(view) {
    private var data: SchedulerItem?=null

    init {

    }

    fun bind(item: SchedulerItem) {
        data = item

        itemView.tv_video_title.text = item.videoTitle
        itemView.tv_time_scheduler.text = item.time
        itemView.tv_content_video_duration.text = item.duration
        itemView.tv_content_video_level.text = item.level
        itemView.tv_content_video_instructor.text = item.instructor
        itemView.tv_content_video_collection.text = item.collection
    }
}