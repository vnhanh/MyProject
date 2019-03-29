package com.tma.movemind.ui.fragment_time_table


import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.tma.movemind.R
import com.tma.movemind.base.customs.ItemDecorationBottomCustom
import com.tma.movemind.base.dagger.ui.BaseFragment
import com.tma.movemind.ui.fragment_time_table.recyclerview.SchedulerAdapter
import com.tma.movemind.ui.fragment_time_table.recyclerview.SchedulerItem
import kotlinx.android.synthetic.main.fragment_time_table.*
import javax.inject.Inject


class TimeTableFragment : BaseFragment(), ITimeTableContract.View {
    @Inject lateinit var presenter:ITimeTableContract.Presenter

    companion object {
        val TAG = "TimeTableFragment"
        private var INSTANCE:TimeTableFragment?=null

        fun getInstance() : TimeTableFragment {
            if(INSTANCE == null){
                INSTANCE = TimeTableFragment()
            }

            return INSTANCE!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_table, container, false)
    }

    override fun onStart() {
        super.onStart()

        setupUI()
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach(this)
    }

    override fun onPause() {
        presenter.onDetach()
        super.onPause()
    }

    private fun setupUI() {
        btn_back_screen_now_playing.setOnClickListener {
            activity?.onBackPressed()
        }

        btn_time_table_today.setOnClickListener {
            selectBtnItem(btn_time_table_today)
            unselectBtnItem(btn_time_table_tomorrow)
        }

        btn_time_table_tomorrow.setOnClickListener {
            selectBtnItem(btn_time_table_tomorrow)
            unselectBtnItem(btn_time_table_today)
        }

        recyclerview_scheduler_videos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview_scheduler_videos.setHasFixedSize(true)
        val adapter = SchedulerAdapter(object : SchedulerAdapter.OnClickItemListener {
            override fun onClick(item: SchedulerItem) {

            }

        })
        val list = ArrayList<SchedulerItem>()
        val item = SchedulerItem()
        item.time = "6:30 - 7:00"
        item.videoTitle = "Mini Strength Booster"
        item.duration = "3:00"
        item.level = "Beginner"
        item.instructor = "Adam Doe"
        item.collection = "STRENGTH"
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        list.add(item)
        adapter.setList(list)
        recyclerview_scheduler_videos.adapter = adapter

        context?.let{context ->
            ContextCompat.getDrawable(context, R.drawable.bg_gray_rectangle_divider)?.let { dividerDrawable ->
                val dividerHeight = resources.getDimensionPixelSize(R.dimen.size_divider)
                val decoration = ItemDecorationBottomCustom(dividerDrawable, dividerHeight)

                recyclerview_scheduler_videos.addItemDecoration(decoration)
            }
        }
    }

    private var normalTextSize = 14f
    private var expandedTextSize = 16f
    private val durationAnim = 300L
    private var selectTextViewAnimator:ValueAnimator?=null
    private var unselectTextViewAnimator:ValueAnimator?=null

    private fun selectBtnItem(tv: TextView) {
        tv.setTypeface(Typeface.DEFAULT_BOLD)

        if(selectTextViewAnimator == null){
            selectTextViewAnimator = ValueAnimator.ofFloat(normalTextSize, expandedTextSize)?.also { anim ->
                anim.duration = durationAnim
                anim.addUpdateListener {
                    val animatedValue:Float = it.animatedValue as Float
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, animatedValue)
                }
                anim.addListener(object : Animator.AnimatorListener{
                    override fun onAnimationRepeat(p0: Animator?) {

                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        anim.removeListener(this)
                        selectTextViewAnimator = null
                    }

                    override fun onAnimationCancel(p0: Animator?) {

                    }

                    override fun onAnimationStart(p0: Animator?) {

                    }

                })
                anim.start()
            }
        }
    }

    private fun unselectBtnItem(tv: TextView) {
        tv.setTypeface(Typeface.DEFAULT)

        if(unselectTextViewAnimator == null){
            unselectTextViewAnimator = ValueAnimator.ofFloat(expandedTextSize, normalTextSize)?.also { anim ->
                anim.duration = durationAnim
                anim.addUpdateListener {
                    val animatedValue:Float = it.animatedValue as Float
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, animatedValue)
                }
                anim.addListener(object : Animator.AnimatorListener{
                    override fun onAnimationRepeat(p0: Animator?) {

                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        anim.removeListener(this)
                        unselectTextViewAnimator = null
                    }

                    override fun onAnimationCancel(p0: Animator?) {

                    }

                    override fun onAnimationStart(p0: Animator?) {

                    }

                })
                anim.start()
            }
        }
    }
}
