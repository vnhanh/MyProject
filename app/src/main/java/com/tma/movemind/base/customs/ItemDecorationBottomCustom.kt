package com.tma.movemind.base.customs

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.tma.movemind.base.utils.Constant.TAG_LOG

class ItemDecorationBottomCustom(val dividerDrawable: Drawable, val dividerSize:Int) : RecyclerView.ItemDecoration() {
    // flag confirms drawing divider from margin of the item of recyclerview
    // would be use in the future
    val isDrawingFromMargin = false

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect?.top = dividerSize
    }

    override fun onDraw(canvas: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        if(canvas == null || parent == null)
            return

        val childCount = parent.childCount
        for (index in 0 until childCount){
            val child = parent.getChildAt(index)
            drawBottomEdge(child, canvas)
        }
    }

    private fun drawBottomEdge(child: View?, canvas: Canvas) {
        if(child == null)
            return

        val params = child.layoutParams as RecyclerView.LayoutParams
        val dividerLeft = child.left - if(isDrawingFromMargin) params.leftMargin else 0
        val dividerRight = child.right + if(isDrawingFromMargin) params.rightMargin else 0
        val dividerTop = child.bottom + params.bottomMargin
        val dividerBottom = dividerTop + dividerSize

        dividerDrawable.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
        dividerDrawable.draw(canvas)
    }
}