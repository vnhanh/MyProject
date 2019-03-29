package vn.com.tma.vo_ngoc_hanh.network.datasource

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import vn.com.tma.vo_ngoc_hanh.network.models.ResponseValue
import vn.com.tma.vo_ngoc_hanh.network.models.UserData

/**
 * Managing local database
 */
class LoginLocalDataSource : LoginDataSource {
    override fun getUserData(uid: String): Flowable<ResponseValue<UserData>> {
        // fake data
        val userData = UserData("em100200", "vnhanh", "vnhanh@tma.com.vn", "0388900789")
        val response = ResponseValue<UserData>(200, "", userData)
        return Observable.just(response).toFlowable(BackpressureStrategy.BUFFER)
    }
}