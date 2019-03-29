package vn.com.tma.vo_ngoc_hanh.network.datasource

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import vn.com.tma.vo_ngoc_hanh.network.ApiUtil
import vn.com.tma.vo_ngoc_hanh.network.models.ResponseValue
import vn.com.tma.vo_ngoc_hanh.network.models.UserData
import vn.com.tma.vo_ngoc_hanh.network.services.LoginService

/**
 * Managing remote data
 */
class LoginRemoteDataSource : LoginDataSource {
    private lateinit var loginService: LoginService

    init {
        loginService = ApiUtil.getLoginService()
    }

    override fun getUserData(uid: String): Flowable<ResponseValue<UserData>> {
        return loginService.getUserData("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).toFlowable(BackpressureStrategy.BUFFER)
    }
}