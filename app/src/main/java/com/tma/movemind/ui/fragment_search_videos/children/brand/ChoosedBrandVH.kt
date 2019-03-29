package com.tma.movemind.ui.fragment_search_videos.children.brand

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tma.movemind.R
import com.tma.movemind.base.dagger.ui.BaseVH
import com.tma.movemind.ui.fragment_search_videos.SearchVideosFragment
import kotlinx.android.synthetic.main.viewholder_search_videos_normal.view.*
import com.tma.movemind.base.models.Brand

class ChoosedBrandVH(view:View, listener: ChoosedBrandsAdapter.OnItemClickListener?) : BaseVH<Brand>(view) {
    private var brand: Brand?=null
    init {
        view.setOnClickListener {
            listener?.onClick(brand)
        }
    }

    fun bind(_brand: Brand, position: Int){
        brand = _brand
        itemView.tv_value.text = _brand.name

        if(itemView is ConstraintLayout){
            val parent:ConstraintLayout = itemView

            val set = ConstraintSet()
            set.clone(parent)
            when(position){
                0 -> {
                    set.connect(R.id.tv_value, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, SearchVideosFragment.marginx2)
                }

                1 -> {
                    set.connect(R.id.tv_value, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, SearchVideosFragment.marginx2)
                }
            }
            set.applyTo(parent)
        }
    }
}