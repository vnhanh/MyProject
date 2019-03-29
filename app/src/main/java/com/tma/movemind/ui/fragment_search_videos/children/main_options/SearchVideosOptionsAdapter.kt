package com.tma.movemind.ui.fragment_search_videos.children.main_options

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tma.movemind.R
import com.tma.movemind.base.utils.Constant
import java.lang.ref.WeakReference

class SearchVideosOptionsAdapter(listener: OnItemClickListener?) : RecyclerView.Adapter<SearchVideosOptionVH>() {
    private var list = ArrayList<SEARCH_OPTION>()
    private var weakListener:WeakReference<OnItemClickListener>?=null

    init {
        if(listener != null)
            weakListener = WeakReference(listener)
    }

    fun setList(_list:ArrayList<SEARCH_OPTION>){
        Log.d(Constant.TAG_LOG, "number of items: ${_list.size}")
        list = _list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVideosOptionVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_search_videos_one_or_two_rows, parent, false)
        return SearchVideosOptionVH(view, weakListener?.get())
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SearchVideosOptionVH, position: Int) {
        holder.bind(list.get(position), position)
    }

    interface OnItemClickListener{
        fun onClick(value:SEARCH_OPTION)
    }
}