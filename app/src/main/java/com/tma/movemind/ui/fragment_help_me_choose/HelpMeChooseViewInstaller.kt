package com.tma.movemind.ui.fragment_help_me_choose

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.tma.movemind.R
import com.tma.movemind.base.utils.StringUtil
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.util.Log
import com.tma.movemind.base.utils.Constant.TAG_LOG


class HelpMeChooseViewInstaller(val context:Context) {

    var constraintLayout:ConstraintLayout?=null // parent view
    var mostTopView: View?=null // top view of all views - the first top view will align right bottom of this top view

    val WRAP_CONTENT = ConstraintLayout.LayoutParams.WRAP_CONTENT

    var maxHeightItem = 0

    //init vars
    var titleSize = 0f // text size of title views
    var itemHeight = 0 // height of each item
    var marginTopFirstTile = 0
    var marginTopNotFirstTitle = 0 // marginTop of title views that are not the first title view
    var marginTopListView = 0 // marginTop of recyclerview
    var defaultMargin = 0 // default marginLeft of all views

    /**
     * Inits all needed values such as height of each item depends on height of drawing space and number of rows (options)
     * @param heightAvailableSpace: height of available space that will draw views
     */
    fun onInit(heightAvailableSpace:Int, rowNumbers:Int){
        titleSize = 13f
        defaultMargin = context.resources.getDimensionPixelSize(R.dimen.marginx2)
        maxHeightItem = context.resources.getDimensionPixelSize(R.dimen.max_height_a_list_help_me_choose)
        marginTopListView = context.resources.getDimensionPixelSize(R.dimen.margin)
        marginTopFirstTile = context.resources.getDimensionPixelSize(R.dimen.margin)
        marginTopNotFirstTitle = context.resources.getDimensionPixelSize(R.dimen.marginx2)

        this.availableSpaceHeight = heightAvailableSpace
        this.rowNumbers = rowNumbers
//        calculateSize(heightAvailableSpace, rowNumbers)

        draw()
    }

    private var titleHeight=0
    private var availableSpaceHeight=0
    private var rowNumbers=0

    private fun calculateSize(availableSpaceHeight: Int, rowNumbers: Int) {
        Log.d(TAG_LOG, "availableSpaceHeight: ${availableSpaceHeight} | titleHeigth: ${titleHeight}")
        val totalTitlesSpaceHeight = titleHeight * rowNumbers + marginTopFirstTile + (rowNumbers - 1) * marginTopNotFirstTitle
        val totalMarginTopListView = rowNumbers * marginTopListView
        itemHeight = (availableSpaceHeight - totalMarginTopListView - totalTitlesSpaceHeight) / rowNumbers
    }

    private fun getTitleHeight() : Int{
        val paint = TextPaint()
        val bounds = Rect()

        val textSize = titleSize * context.resources.displayMetrics.density
        paint.setTextSize(textSize)// have this the same as your text size

        val text = "TODAY I FELL LIKE"

        paint.getTextBounds(text, 0, text.length, bounds)

        return bounds.height()
    }

    private fun draw() {
        if(constraintLayout == null || mostTopView == null) return
        var bottomView = drawTodayLikeRow(mostTopView!!)
        bottomView = drawNumberMinutesIHave(bottomView)
        bottomView = drawIWannaFocusOn(bottomView)
        drawLevelIAm(bottomView)
    }

    private fun drawTodayLikeRow(topView:View):View {
        val titleText = StringUtil.getString(context, R.string.title_today_i_feel_like)
        calculateSize(availableSpaceHeight, rowNumbers)
        return drawARow(topView, titleText, marginTopFirstTile)
    }

    private fun drawNumberMinutesIHave(topView: View) : View {
        val titleText = StringUtil.getString(context, R.string.title_today_i_have_minutes)
        return drawARow(topView, titleText, marginTopNotFirstTitle)
    }

    private fun drawIWannaFocusOn(topView: View) : View {
        val titleText = StringUtil.getString(context, R.string.title_i_wanna_focus_on_my_)
        return drawARow(topView, titleText, marginTopNotFirstTitle)
    }

    private fun drawLevelIAm(topView: View) : View{
        val titleText = StringUtil.getString(context, R.string.title_i_am_a_level)
        return drawARow(topView, titleText, marginTopNotFirstTitle)
    }

    private fun drawARow(topView: View, titleText:String, marginTopTitleView:Int) : View {
        val tv = createTextView(titleText, topView, marginTopTitleView)
        val rv = createRecyclerView(tv, marginTopListView)

        return rv
    }

    private fun createTextView(text:String, topView:View, marginTop:Int) : TextView {
        val tv = TextView(context)
        tv.setText(text)
        tv.id = ViewCompat.generateViewId()

        val params = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        tv.layoutParams = params
        if(titleHeight == 0){
            var width = 0
            var heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            tv.measure(width, heightMeasureSpec)
            titleHeight = tv.measuredHeight
            Log.d(TAG_LOG, "installer | calculate | titleHeight: ${titleHeight}")
        }
        constraintLayout?.addView(tv, tv.id)

        // set style
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize)
        tv.setTypeface(tv.typeface, Typeface.BOLD)

        // set constraints
        setupConstraints(constraintLayout, topView, tv, marginLeft = defaultMargin, marginTop = marginTop)

        return tv
    }

    private fun createRecyclerView(topView:View, marginTop:Int) : RecyclerView {
        val rv = RecyclerView(context)
        rv.id = ViewCompat.generateViewId()

        Log.d(TAG_LOG, "installer | itemHeight: ${itemHeight}")
        val params = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, itemHeight)
        rv.layoutParams = params
        rv.setBackgroundColor(Color.BLUE)

        constraintLayout?.addView(rv, rv.id)

        // set style
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // set constraints
        setupConstraints(constraintLayout, topView, rv, marginLeft = defaultMargin, marginTop = marginTop, marginRight = defaultMargin)
        return rv
    }

    private fun setupConstraints(marginView:View?, topView:View?, child:View, marginLeft:Int, marginTop:Int, marginRight:Int=-1){
        if(marginView == null || topView == null) return

        val set = ConstraintSet()
        set.clone(constraintLayout)
        set.connect(child.id, ConstraintSet.TOP, topView.id, ConstraintSet.BOTTOM, marginTop)
        set.connect(child.id, ConstraintSet.LEFT, marginView.id, ConstraintSet.LEFT, marginLeft)
        if(marginRight > -1)
            set.connect(child.id, ConstraintSet.RIGHT, marginView.id, ConstraintSet.RIGHT, marginRight)

        set.applyTo(constraintLayout)
    }
}