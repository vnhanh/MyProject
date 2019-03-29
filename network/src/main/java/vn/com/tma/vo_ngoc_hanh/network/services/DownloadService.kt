package vn.com.tma.vo_ngoc_hanh.network.services

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming
import retrofit2.http.Url


interface DownloadService {
    @GET
    @Streaming
    fun downloadFileWithFixedUrl(@Url downloadUrl:String, @Header("Range") downloadRange:String): Observable<ResponseBody>

    @GET
    @Streaming
    fun getResponseBodyWithUrl(@Url downloadUrl:String, @Header("Range") downloadRange:String): Call<ResponseBody>
}