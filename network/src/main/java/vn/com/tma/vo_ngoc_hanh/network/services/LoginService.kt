package vn.com.tma.vo_ngoc_hanh.network.services

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import vn.com.tma.vo_ngoc_hanh.network.models.ResponseCustomize
import vn.com.tma.vo_ngoc_hanh.network.models.ResponseValue
import vn.com.tma.vo_ngoc_hanh.network.models.UserData

/**
 * An example for the service
 *
 */
interface LoginService {
    @GET("getUser/{uid}")
    fun getUserData(@Path("uid") uid:String) : Observable<ResponseValue<UserData>>

    @GET("getUser/{uid}")
    fun getUserDataSecond(@Path("uid") uid:String) : ResponseCustomize<ResponseValue<UserData>>
}