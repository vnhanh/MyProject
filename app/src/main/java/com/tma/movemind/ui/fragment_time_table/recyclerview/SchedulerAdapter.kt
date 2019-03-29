package com.tma.movemind.ui.fragment_time_table.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tma.movemind.R
import java.lang.ref.WeakReference

class SchedulerAdapter(listener: OnClickItemListener) : RecyclerView.Adapter<SchedulerItemVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchedulerItemVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_scheduler_event_practice, parent, false)

        return SchedulerItemVH(view, weakListener)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SchedulerItemVH, position: Int) {
        holder.bind(list.get(position))
    }

    private var list = ArrayList<SchedulerItem>()
    private var weakListener:WeakReference<OnClickItemListener>?=null

    init {
        weakListener = WeakReference(listener)
    }

    fun setList(_list:ArrayList<SchedulerItem>){
        list = _list
        notifyDataSetChanged()
    }

    interface OnClickItemListener{
        fun onClick(item: SchedulerItem)
    }
}