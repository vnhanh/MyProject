package vn.com.tma.vo_ngoc_hanh.network.download

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import kotlin.collections.ArrayList


class NetworkManager() : BroadcastReceiver(){
    private val TAG = this.javaClass.name
    private val listeners = ArrayList<OnChangeNetworkState>()

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.also {context ->
            intent?.also { intent ->
                val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                val activeNetInfo = cm.activeNetworkInfo

                val isConnected = activeNetInfo != null && activeNetInfo.isConnected

                if (isConnected){
                    Log.d(TAG, "Connected")
                }
                else {
                    Log.d(TAG, "Not Connected")
                }
                notifyAllListeners(isConnected)
            }
        }
    }

    private fun notifyAllListeners(isConnected: Boolean) {
        for (listener in listeners){
            listener.onChangeState(isConnected)
        }
    }

    fun addListener(listener: OnChangeNetworkState){
        if(!listeners.contains(listener))
            listeners.add(listener)
    }

    fun removeListener(listener: OnChangeNetworkState){
        listeners.remove(listener)
    }

    interface OnChangeNetworkState{
        fun onChangeState(isConnected:Boolean)
    }
}