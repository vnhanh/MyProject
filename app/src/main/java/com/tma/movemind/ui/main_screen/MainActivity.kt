package com.tma.movemind.ui.main_screen

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.tma.movemind.R
import com.tma.movemind.base.utils.Constant
import com.tma.movemind.base.utils.Util
import com.tma.movemind.ui.fragment_home.HomeFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
    }

    private fun setupUI() {
        val fragment = HomeFragment.getInstance()
        val newTag = HomeFragment.TAG
        Util.addOrShowFragment(supportFragmentManager, fragment, newTag, null, R.id.frame_layout_home, isAddToBackStack = false)
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager

        for (frag in fm.fragments) {
            if (frag.isVisible) {
                val childFm = frag.childFragmentManager
                if (childFm.backStackEntryCount > 0) {
                    childFm.popBackStack()
                    return
                }
            }
        }
        super.onBackPressed()
    }

//    override fun onWindowFocusChanged(hasFocus: Boolean) {
//        super.onWindowFocusChanged(hasFocus)
//        Log.d(Constant.TAG_LOG, "MainActivity | onWindowFocusChanged: ${hasFocus}")
//        isWindowFocused = hasFocus
//        for (listener in windowFocusChangedListeners){
//            listener.onWindowFocusChanged()
//        }
//    }

//    var isWindowFocused:Boolean = false
//
//    fun addOnWindowFocusChangedListener(listener:OnWindowFocusChangedListener){
//        Log.d(Constant.TAG_LOG, "mainActivity | addOnWindowFocusChangedListener")
//        windowFocusChangedListeners.add(listener)
//    }
//
//    fun removeOnWindowFocusChangedListener(listener:OnWindowFocusChangedListener){
//        windowFocusChangedListeners.remove(listener)
//    }
//
//    private val windowFocusChangedListeners = ArrayList<OnWindowFocusChangedListener>()
//
//    interface OnWindowFocusChangedListener{
//        fun onWindowFocusChanged()
//    }
}