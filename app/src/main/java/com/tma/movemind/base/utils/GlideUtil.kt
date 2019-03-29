package com.tma.movemind.base.utils

import com.bumptech.glide.request.RequestOptions
import com.tma.movemind.R

object GlideUtil {
    fun getDefaultRequestOptions(width:Int, height:Int) : RequestOptions {
        return RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.bg_rectangle_white_corner_4dp)
                .error(R.drawable.bg_rectangle_gray_corner_4dp)
                .override(width, height)
    }
}