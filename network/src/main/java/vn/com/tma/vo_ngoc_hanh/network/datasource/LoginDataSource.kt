package vn.com.tma.vo_ngoc_hanh.network.datasource

import io.reactivex.Flowable
import vn.com.tma.vo_ngoc_hanh.network.models.ResponseValue
import vn.com.tma.vo_ngoc_hanh.network.models.UserData

/**
 * An example of a datasource interface
 * This class define the way of process on data, database and remote server like add, get, save, delete etc.
 */
interface LoginDataSource {
    fun getUserData(uid:String) : Flowable<ResponseValue<UserData>>
}