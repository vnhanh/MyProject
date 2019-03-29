package com.tma.movemind.base.customs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import vn.com.tma.custom_exoplayer.Constant.TAG_LOG


/**
 * Defining drawing item decoration in recyclerview
 * @spanNumber: number of columns
 */
class ItemDecorationGridHorizontal(val divider: Drawable?, val dividerSize:Int, val spanNumber:Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        if(parent == null || view == null) return
        val index = parent.indexOfChild(view)
        val outTop:Int = if(index % spanNumber == 0) dividerSize else 0
        val outLeft:Int = if(index < spanNumber) dividerSize else 0

        if(index % spanNumber == 0){
            outRect?.set(outLeft,outTop,dividerSize, dividerSize)
        }else{
            outRect?.set(outLeft,outTop,dividerSize, dividerSize)
        }
    }

    override fun onDraw(canvas: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        if(canvas == null || parent == null || state == null || divider == null)
            return

        val childCount = parent.getChildCount()

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            drawRightEdge(child, canvas)
            drawBottomEdge(child, canvas)

            if(i % spanNumber == 0)
                drawTopEdge(child, canvas)

            if(i < spanNumber)
                drawLeftEdge(child, canvas)
        }

    }

    private fun drawTopEdge(child: View, canvas: Canvas){
        val dividerBottom = child.top
        val dividerTop = dividerBottom - dividerSize
        val dividerLeft = child.left - dividerSize
        val dividerRight = child.right + dividerSize

        drawDivider(dividerLeft, dividerTop, dividerRight, dividerBottom, canvas)
    }

    private fun drawBottomEdge(child: View, canvas: Canvas){
        val dividerTop = child.bottom
        val dividerBottom = dividerTop + dividerSize
        val dividerLeft = child.left - dividerSize
        val dividerRight = child.right + dividerSize

        drawDivider(dividerLeft, dividerTop, dividerRight, dividerBottom, canvas)
    }

    private fun drawLeftEdge(child: View, canvas: Canvas){
        val dividerTop = child.top - dividerSize
        val dividerBottom = child.bottom + dividerSize
        val dividerRight = child.left
        val dividerLeft = dividerRight - dividerSize

        drawDivider(dividerLeft, dividerTop, dividerRight, dividerBottom, canvas)
    }

    private fun drawRightEdge(child: View, canvas: Canvas){
        val dividerTop = child.top - dividerSize
        val dividerBottom = child.bottom + dividerSize
        val dividerLeft = child.right
        val dividerRight = dividerLeft + dividerSize

        drawDivider(dividerLeft, dividerTop, dividerRight, dividerBottom, canvas)
    }

    private fun drawDivider(left:Int, top:Int, right:Int, bottom:Int, canvas: Canvas){
        divider?.setBounds(left, top, right, bottom)
        divider?.draw(canvas)
    }

    private constructor(builder:Builder) : this(builder.dividerDrawable, builder.dividerPixelSize, builder.spanNumber)

    data class Builder(val context: Context){
        var dividerDrawable:Drawable ?= null
        var dividerPixelSize = 0
        var spanNumber = 1

        fun dividerDrawableResID(@DrawableRes id:Int) = apply { dividerDrawable = ContextCompat.getDrawable(context, id) }
        fun dividerSizeResID(@DimenRes size:Int) = apply { dividerPixelSize = context.resources.getDimensionPixelSize(size) }
        fun spanNumber(number:Int) = apply {spanNumber = number}
        fun build() = ItemDecorationGridHorizontal(this)
    }
}