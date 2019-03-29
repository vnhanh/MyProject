package com.tma.movemind.base.customs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Defining drawing item decoration in recyclerview
 * @spanNumber: number of columns
 */
class ItemDecorationGridVertical(val divider:Drawable?, val dividerSize:Int, val spanNumber:Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect?.set(dividerSize, dividerSize,dividerSize,dividerSize)
    }

    override fun onDraw(canvas: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        if(canvas == null || parent == null || state == null || divider == null)
            return

        val childCount = parent.getChildCount()

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            drawBottomEdge(child, canvas)

            val isFirstRow = (i / spanNumber) == 0
            val isNotRightEdge = (i % spanNumber) != (spanNumber-1)

            if(isFirstRow){
                drawTopEdge(child, canvas)
            }

            if(isNotRightEdge){
                drawRightEdge(child, canvas)
            }
        }
    }

    private fun drawTopEdge(child:View, canvas: Canvas){
        val params = child.layoutParams as RecyclerView.LayoutParams

        val dividerBottom = child.top - params.topMargin
        val dividerTop = dividerBottom - dividerSize
        val dividerLeft = child.left - params.leftMargin - dividerSize
        val dividerRight = child.right + params.rightMargin + dividerSize

        drawDivider(dividerLeft, dividerTop, dividerRight, dividerBottom, canvas)
    }

    private fun drawBottomEdge(child:View, canvas: Canvas){
        val params = child.layoutParams as RecyclerView.LayoutParams

        val dividerTop = child.bottom + params.bottomMargin
        val dividerBottom = dividerTop + dividerSize
        val dividerLeft = child.left - params.leftMargin - dividerSize
        val dividerRight = child.right + params.rightMargin + dividerSize

        drawDivider(dividerLeft, dividerTop, dividerRight, dividerBottom, canvas)
    }

    private fun drawLeftEdge(child:View, canvas: Canvas){
        val params = child.layoutParams as RecyclerView.LayoutParams

        val dividerTop = child.top - params.topMargin
        val dividerBottom = child.bottom + params.bottomMargin
        val dividerRight = child.left - params.leftMargin
        val dividerLeft = dividerRight - dividerSize

        drawDivider(dividerLeft, dividerTop, dividerRight, dividerBottom, canvas)
    }

    private fun drawRightEdge(child:View, canvas: Canvas){
        val params = child.layoutParams as RecyclerView.LayoutParams

        val dividerTop = child.top - params.topMargin - child.paddingTop
        val dividerBottom = child.bottom + params.bottomMargin + child.paddingBottom
        val dividerLeft = child.right + params.rightMargin
        val dividerRight = dividerLeft + dividerSize

        drawDivider(dividerLeft, dividerTop, dividerRight, dividerBottom, canvas)
    }

    private fun drawDivider(left:Int, top:Int, right:Int, bottom:Int, canvas:Canvas){
        divider?.setBounds(left, top, right, bottom)
        divider?.draw(canvas)
    }

    private constructor(builder:Builder) : this(builder.dividerDrawable, builder.dividerPixelSize, builder.spanNumber)

    data class Builder(val context:Context){
        var dividerDrawable:Drawable ?= null
        var dividerPixelSize = 0
        var spanNumber = 1

        fun dividerDrawableResID(@DrawableRes id:Int) = apply { dividerDrawable = ContextCompat.getDrawable(context, id) }
        fun dividerSizeResID(@DimenRes size:Int) = apply { dividerPixelSize = context.resources.getDimensionPixelSize(size) }
        fun spanNumber(number:Int) = apply {spanNumber = number}
        fun build() = ItemDecorationGridVertical(this)
    }
}