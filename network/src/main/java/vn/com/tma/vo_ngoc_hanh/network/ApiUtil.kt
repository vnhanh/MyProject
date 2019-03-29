package vn.com.tma.vo_ngoc_hanh.network

import vn.com.tma.vo_ngoc_hanh.network.services.DownloadService
import vn.com.tma.vo_ngoc_hanh.network.services.LoginService

/**
 * This class defines ApiServices generated from interfaces by Retrofit framework.
 * ApiServices allow app to communicate to server side through methods like GET, POST, PUT, DELETE that defined before in interfaces
 *
 * Extra: package "datasource": it contains repositories as well as local and remote datasources.
 */
open class ApiUtil {
    companion object {
        // determine servers's addresses here
        private val BASE_SERVER = ""

        // An example
        fun getLoginService(): LoginService {
            return RetrofitClient.getClient("http://192.168.36.170:9090/").create(LoginService::class.java)
        }

        fun getDownloadService(url:String): DownloadService {
            return RetrofitClient.getClient(url).create(DownloadService::class.java)
        }
    }
}