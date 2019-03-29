package com.tma.movemind.base.dagger.ui

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseVH<M:Any>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    protected var data:M?=null

    open fun bind(_item:M){
        data = _item
    }
}