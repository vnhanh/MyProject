package com.tma.movemind.ui.fragment_search_videos.children.main_options

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tma.movemind.R
import com.tma.movemind.ui.fragment_search_videos.SearchVideosFragment
import kotlinx.android.synthetic.main.viewholder_search_videos_normal.view.*

class SearchVideosOptionVH(
        view: View,
        val listener: SearchVideosOptionsAdapter.OnItemClickListener?) : RecyclerView.ViewHolder(view) {

    private var option:SEARCH_OPTION = SEARCH_OPTION.UNKNOWN

    init {
        itemView.setOnClickListener {
            listener?.onClick(option)
        }
    }

    fun bind(_value:SEARCH_OPTION, position:Int){
        option = _value
        itemView.tv_value.setText(_value.getText())

        if(itemView is ConstraintLayout){
            val parent:ConstraintLayout = itemView

            val set = ConstraintSet()
            set.clone(parent)
            when(position){
                0 -> {
                    set.connect(R.id.tv_value, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, SearchVideosFragment.marginx3)
                    set.connect(R.id.tv_value, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, SearchVideosFragment.marginx3)
                }

                1 -> {
                    set.connect(R.id.tv_value, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, SearchVideosFragment.marginx3)
                    set.connect(R.id.tv_value, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, SearchVideosFragment.marginx3)
                }

                2 -> {
                    set.connect(R.id.tv_value, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, SearchVideosFragment.marginx3)
                    set.connect(R.id.tv_value, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, SearchVideosFragment.marginx3)
                }

                3 -> {
                    set.connect(R.id.tv_value, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, SearchVideosFragment.marginx3)
                    set.connect(R.id.tv_value, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, SearchVideosFragment.marginx3)
                }
            }
            set.applyTo(parent)
        }
    }
}