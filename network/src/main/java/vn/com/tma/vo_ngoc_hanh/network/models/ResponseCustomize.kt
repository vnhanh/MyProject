package vn.com.tma.vo_ngoc_hanh.network.models

import android.util.Log
import io.reactivex.observers.DisposableObserver

class ResponseCustomize<T> : DisposableObserver<ResponseValue<T>>(){
    protected var TAG = this.javaClass.simpleName

    override fun onNext(t: ResponseValue<T>) {
        when(t.status){
            // sucess
            200 -> onResponseSuccess(t.data)

            // errors

            400 -> onBadRequest(t.error)
            401 -> onUnAuthorized(t.error)
            403 -> onForbidden(t.error)
            404 -> onNotFound(t.error)
            405 -> onMethodNotAllowed(t.error)
            406 -> onNotAcceptable(t.error)
            408 -> onRequestTimeout(t.error)
            410 -> onResourcesGone(t.error)
            414 -> onRequestURITooLong(t.error)
            415 -> onUnsupportedMediaType(t.error)

            500 -> onInternalServerError(t.error)
            501 -> onNotImplemented(t.error)
            503 -> onServiceUnavailable(t.error)
            505 -> onHTTPVersionNotSupported(t.error)

            else -> {
                Log.i(TAG, "onNext() - status code unrecognized: ${t.status}")
            }
        }
    }

    // overrided function
    protected fun onResponseSuccess(data: T) {

    }

    /**
     * Zone of error processing functions
     * Override needed functions to process client or server errors
     */
    // status code: 400
    protected fun onBadRequest(error: String) {

    }

    // status code: 401
    protected fun onUnAuthorized(error: String) {

    }

    // status code: 403
    protected fun onForbidden(error: String) {

    }

    // status code: 404
    protected fun onNotFound(error: String) {

    }

    // status code: 405
    protected fun onMethodNotAllowed(error: String) {

    }

    // status code: 406
    protected fun onNotAcceptable(error: String) {

    }

    // status code: 408
    protected fun onRequestTimeout(error: String) {

    }

    // status code: 410
    protected fun onResourcesGone(error: String) {

    }

    // status code: 414
    protected fun onRequestURITooLong(error: String) {

    }

    // status code: 415
    protected fun onUnsupportedMediaType(error: String) {

    }


    // status code: 500
    protected fun onInternalServerError(error: String) {

    }

    // status code: 501
    protected fun onNotImplemented(error: String) {

    }

    // status code: 503
    protected fun onServiceUnavailable(error: String) {

    }

    // status code: 505
    protected fun onHTTPVersionNotSupported(error: String) {

    }

    /**
     * ------- END Error Zone
     */

    override fun onComplete() {}


    override fun onError(e: Throwable) {
        Log.i(TAG,  "onError(): ${e.message}")
    }
}