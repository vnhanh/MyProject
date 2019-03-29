package com.tma.movemind.base.dagger.ui

import android.support.annotation.IdRes

/**
 * Shows progress information with 1 progress bar and 1 description
 */
interface IProgressTitleView {
    fun showProgress(@IdRes msgRes:Int)
    fun showProgress(message:String)
    fun hideProgress()
}