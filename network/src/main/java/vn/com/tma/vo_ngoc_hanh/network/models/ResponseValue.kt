package vn.com.tma.vo_ngoc_hanh.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * A base class. All Response objects will inheritance it.
 */
class ResponseValue<T>(
        @SerializedName("status")
        @Expose
        var status:Int,
        @SerializedName("error")
        @Expose
        var error:String,
        @SerializedName("data")
        @Expose
        var data:T
)