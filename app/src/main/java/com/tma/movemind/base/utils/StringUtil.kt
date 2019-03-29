package com.tma.movemind.base.utils

import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.StringRes

object StringUtil {
    fun isEmpty(str:String?):Boolean = str == null || "".equals(str)

    fun isNotEmpty(str:String?) : Boolean = !isEmpty(str)

    fun getString(context: Context, @StringRes idRes: Int) : String = context.getString(idRes)

    fun convertDurationToString(duration:Int /*seconds unit*/) : String{
        if(duration < 0) return ""
        when(duration){
            in 0..59 -> {
                return String.format("0 : %s", duration)
            }
            in 60..3559 -> {
                val minute = duration / 60
                val sec = duration % 60
                return String.format("%d : %d", minute, sec)
            }
            else -> {
                val hour = duration / 3600
                val minute = (duration % 3600) / 60
                val sec = duration % 60
                return String.format("%d : %d : %d", hour, minute, sec)
            }
        }
    }
}