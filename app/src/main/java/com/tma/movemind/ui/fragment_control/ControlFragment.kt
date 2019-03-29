package com.tma.movemind.ui.fragment_control


import android.animation.ValueAnimator
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.view.ContextThemeWrapper
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast

import com.tma.movemind.R
import com.tma.movemind.base.utils.Util
import com.tma.movemind.ui.fragment_help_me_choose.HelpMeChooseFragment
import com.tma.movemind.ui.fragment_search_videos.SearchVideosFragment
import com.tma.movemind.ui.fragment_time_table.TimeTableFragment
import kotlinx.android.synthetic.main.fragment_control.*


class ControlFragment : Fragment() {

    private lateinit var popupMenu:PopupMenu

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_control, container, false)
    }

    override fun onStart() {
        super.onStart()
        initValues()
        setupUI()
    }

    private fun initValues() {
        val contextWrapper = ContextThemeWrapper(context, R.style.PopupMenuCustom)
        if(Build.VERSION.SDK_INT >= 19){
            popupMenu = PopupMenu(contextWrapper, view_pivot_above_menu, Gravity.END)
        }else{
            popupMenu = PopupMenu(contextWrapper, view_pivot_above_menu)
        }
        popupMenu.menuInflater.inflate(R.menu.context_main_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener {
            Toast.makeText(context, "Clicked menu item ${it.title}", Toast.LENGTH_LONG).show()
            false
        }

        popupMenu.setOnDismissListener(object : PopupMenu.OnDismissListener{
            override fun onDismiss(popupMenu: PopupMenu?) {
                hasChangedIconMenuButtonInAnimation = false
                animValue.start()
            }
        })
    }

    // flag confirm "Button Menu" is showing "icon menu"
    // isMenuButtonIcon = false : "Button Menu" is showing "icon close"
    private var isMenuButtonIcon = true
    // flag support for rotate animation of "Button Menu"
    // hasChangedIconMenuButtonInAnimation = false: In animation progress, "Button Menu" has not change its icon (from menu to close and reserve)
    private var hasChangedIconMenuButtonInAnimation = false
    private lateinit var animValue:ValueAnimator
    private val DURATION = 400L
    private val CHANGED_ICON_DEGREE = 180
    private val MAX_DEGREE = 360

    private fun initRotateAnimations(){
        animValue = ValueAnimator.ofInt(0, MAX_DEGREE)
        animValue.addUpdateListener {
            val degree = it.animatedValue as Int
            btn_menu.rotation = degree.toFloat()

            if(!hasChangedIconMenuButtonInAnimation && degree >= CHANGED_ICON_DEGREE){
                if(isMenuButtonIcon){
                    btn_menu.setImageResource(R.drawable.ic_close_black_48dp)
                    popupMenu.show()
                }else{
                    btn_menu.setImageResource(R.drawable.ic_menu_black_48dp)
                }
                hasChangedIconMenuButtonInAnimation = true
                isMenuButtonIcon = !isMenuButtonIcon
            }

            if(degree == MAX_DEGREE){
                btn_menu.rotation = 0f
                if(!isMenuButtonIcon)
                    popupMenu.show()
            }
        }
        animValue.duration = DURATION
    }

    private fun setupUI() {
        arguments?.also { bundle ->
            if(bundle.containsKey(KEY_MODE)){
                val mode = bundle.getString(KEY_MODE)

                when(mode){
                    SHOW_SCHEDULERS -> loadSchedulersUI()
                    MODE_SEARCH -> loadSearchUI()
                    MODE_HELP_CHOOSE -> loadChooseUI()
                }
            }
        }

        initRotateAnimations()

        btn_menu.setOnClickListener {
            hasChangedIconMenuButtonInAnimation = false
            animValue.start()

        }

        setupVolume()
    }

    private fun setupVolume() {
        btn_volume.setOnClickListener {
            if(seekbar_volume.visibility != View.VISIBLE)
                seekbar_volume.visibility = View.VISIBLE
            else
                seekbar_volume.visibility = View.INVISIBLE
        }

        seekbar_volume.setOnClickListener {

        }
    }

    private fun loadSchedulersUI() {
        val fm = childFragmentManager
        val newTag = TimeTableFragment.TAG
        val newFragment = TimeTableFragment.getInstance()
        Util.addOrShowFragment(fm, newFragment, newTag, null, R.id.frame_layout_control, isAddToBackStack = false)
    }

    private fun loadSearchUI() {
        val fm = childFragmentManager
        val newTag = SearchVideosFragment.TAG
        val newFragment = SearchVideosFragment.getInstance()
        Util.addOrShowFragment(fm, newFragment, newTag, null, R.id.frame_layout_control, isAddToBackStack = false)
    }

    private fun loadChooseUI() {
        val fm = childFragmentManager
        val newTag = HelpMeChooseFragment.TAG
        val newFragment = HelpMeChooseFragment.getInstance()
        Util.addOrShowFragment(fm, newFragment, newTag, null, R.id.frame_layout_control, isAddToBackStack = false)
    }


    companion object {
        val TAG = "ControlFragment"

        val MODE_SEARCH = "MODE SEARCH"
        val MODE_HELP_CHOOSE = "HELP ME CHOOSE"
        val SHOW_SCHEDULERS = "SHOW SCHEDULERS"
        val KEY_MODE = "KEY MODE"

        fun getInstance(mode:String) : ControlFragment {
            val instance = ControlFragment()
            val bundle = Bundle()
            bundle.putString(KEY_MODE, mode)
            instance.arguments = bundle

            return instance
        }
    }
}
