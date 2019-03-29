package vn.com.tma.vo_ngoc_hanh.network.datasource

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import vn.com.tma.vo_ngoc_hanh.network.models.ResponseValue
import vn.com.tma.vo_ngoc_hanh.network.models.UserData

class LoginRepository : LoginDataSource {
    lateinit var localDataSource: LoginLocalDataSource
    lateinit var remoteDataSource: LoginRemoteDataSource

    private var cachedData: ResponseValue<UserData>?= null

    override fun getUserData(uid: String): Flowable<ResponseValue<UserData>> {
        if (cachedData != null) {
            return Observable.just(cachedData!!).toFlowable(BackpressureStrategy.BUFFER)
        }

        val remoteData = remoteDataSource.getUserData(uid).doOnNext{_data ->
            //TODO: save new data into local database

            // save it into cached
            cachedData = _data
        }

        return remoteData
    }
}