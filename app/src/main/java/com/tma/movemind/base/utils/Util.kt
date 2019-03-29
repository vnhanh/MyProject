package com.tma.movemind.base.utils

import android.os.Build
import android.support.annotation.AnimRes
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.util.Log
import com.tma.movemind.R
import com.tma.movemind.base.utils.Constant.TAG_LOG
import com.tma.movemind.ui.fragment_results_search_videos.ResultsSearchVideosFragment


object Util {

    fun addOrShowFragment(fm:FragmentManager?, newFragment:Fragment, newFragmentTag:String, curFragTag:String?,
                          @IdRes frame_id:Int, isAddToBackStack: Boolean = true,
                          @AnimRes startAnim:Int? = R.anim.enter_fragment, @AnimRes endAnim:Int? = R.anim.exit_fragment){
        if (fm == null) return
        val ft = fm.beginTransaction()
        if(startAnim != null && endAnim != null)
            ft.setCustomAnimations(startAnim, endAnim)
        val curFragment = getFragmentByTagName(fm, curFragTag)
        val _newFragment = getFragmentByTagName(fm, newFragmentTag)
        if(_newFragment != null){
            ft.show(_newFragment)
        }else{
            ft.add(frame_id, newFragment, newFragmentTag)
        }
        if(curFragment != null)
            ft.hide(curFragment)

        if(isAddToBackStack)
            ft.addToBackStack(null)

        ft.commit()
    }

    fun getFragmentByTagName(fm: FragmentManager, tagName: String?): Fragment? {
        if(tagName == null) return null
        var fragment: Fragment? = null
        val fragmentList: List<Fragment> = fm.fragments
        for (i in 0 until fragmentList.size) {
            val frag = fragmentList[i]
            if (frag.tag != null)
                if (frag.tag == tagName) {
                    fragment = frag
                }
        }
        return fragment
    }

    fun printActivityFragmentList(fragmentManager: FragmentManager) {
        // Get all Fragment list.
        val fragmentList = fragmentManager.fragments

        Log.d(TAG_LOG, "***********************************")
        if (fragmentList != null) {
            val size = fragmentList.size
            for (i in 0 until size) {
                val fragment = fragmentList[i]

                if (fragment != null) {
                    val fragmentTag = fragment.tag
                    Log.d(TAG_LOG, fragmentTag)
                }
            }

            Log.d(TAG_LOG, "***********************************")
        }
    }

    fun <T1,T2> ifNotNull(value1:T1?, value2:T2?, runIfNotNull:(T1,T2) -> (Unit)){
        if(value1 != null && value2 != null){
            runIfNotNull(value1, value2)
        }
    }

    fun getSDKVersion() : Int = Build.VERSION.SDK_INT
}