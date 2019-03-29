package com.tma.movemind.base.dagger.ui

import android.content.Context
import android.support.v4.app.Fragment
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment() : Fragment(), ILifeCycle.View {
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun getViewContext(): Context? = context
}