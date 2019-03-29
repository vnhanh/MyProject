package com.tma.movemind.base.dagger.ui

import android.content.Context

interface ILifeCycle {
    interface View{
        fun getViewContext():Context?
    }

    interface Presenter<in V:View>{
        fun onAttach(view:V)
        fun onDetach()
    }
}