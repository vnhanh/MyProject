package com.tma.movemind.base.dagger.ui

import android.support.v7.widget.RecyclerView
import java.lang.ref.WeakReference

abstract class BaseClickableAdapter<VH:BaseVH<M>, T:Any, M:Any>(listener:T?) : RecyclerView.Adapter<VH>() {
    var list = ArrayList<M>()

    protected var weakListener: WeakReference<T>?=null
    var listener:T?=null
    set(value)  {
        if(value != null)
            weakListener = WeakReference(value)
    }

    init {
        if(listener != null)
            weakListener = WeakReference(listener)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list.get(position))
    }
}