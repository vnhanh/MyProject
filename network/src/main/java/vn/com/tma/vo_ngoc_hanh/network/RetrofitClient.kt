package vn.com.tma.vo_ngoc_hanh.network

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RetrofitClient {
    companion object {
        private val rets:HashMap<String,Retrofit> = HashMap()

        fun getClient(baseUrl:String):Retrofit{
            if (rets.containsKey(baseUrl)) {
                return rets[baseUrl]!!
            }
            val ret:Retrofit = Retrofit.Builder().baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            rets[baseUrl] = ret
            return ret
        }
    }
}