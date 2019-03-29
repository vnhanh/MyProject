package com.tma.movemind.ui.fragment_help_me_choose


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver

import com.tma.movemind.R
import com.tma.movemind.base.dagger.ui.BaseFragment
import com.tma.movemind.base.utils.Constant.TAG_LOG
import com.tma.movemind.ui.main_screen.MainActivity
import kotlinx.android.synthetic.main.fragment_help_me_choose.*
import javax.inject.Inject


class HelpMeChooseFragment : BaseFragment(), IHelpMeChooseContract.View {
    @Inject lateinit var presenter:IHelpMeChooseContract.Presenter

    var viewInstaller: HelpMeChooseViewInstaller?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_me_choose, container, false)
    }

    private var isInstalled = false

    override fun onStart() {
        super.onStart()

        view?.viewTreeObserver?.addOnDrawListener {
            view?.also { parent->
                if(!isInstalled && context != null){
                    viewInstaller = HelpMeChooseViewInstaller(context!!).also {
                        it.constraintLayout = parent_view
                        it.mostTopView = divier
                        val dividerBottom = divier.bottom
                        val parentBottom = parent.bottom
                        val avaiHeight = parentBottom - dividerBottom
                        Log.d(TAG_LOG, "dividerBottom: ${dividerBottom} | parentBottom: ${parentBottom} | avaiHeight: ${avaiHeight}")
                        it.onInit(avaiHeight, 4)
                        isInstalled = !isInstalled
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onAttach(this)
    }

    override fun onPause() {
        presenter.onDetach()
        super.onPause()
    }

    /**
     * @interface IHelpMeChooseContract.View
     */

    override fun getViewContext(): Context? = context

    /**
     * ------------------------
     */

    companion object {
        val TAG = "HelpMeChooseFragment"

        fun getInstance() : HelpMeChooseFragment {
            val instance = HelpMeChooseFragment()

            return instance
        }
    }
}
