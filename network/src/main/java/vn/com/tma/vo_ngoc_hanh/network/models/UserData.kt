package vn.com.tma.vo_ngoc_hanh.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * This class defines the information of an user return to client from server side
 */
class UserData(
        @SerializedName("uid")
        @Expose
        var uid:String?,
        @SerializedName("username")
        @Expose
        var username:String?,
        @SerializedName("email")
        @Expose
        var email:String?,
        @SerializedName("phone")
        @Expose
        var phone:String?
)