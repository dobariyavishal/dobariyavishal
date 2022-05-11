package com.app.weatherapp.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import javax.inject.Inject

open class NetworkConnect @Inject constructor(private val context: Context) {

    fun isConnectedToNetwork(): Boolean {
        val conMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network: Network? = conMgr.activeNetwork
        val networkCapabilities: NetworkCapabilities? = conMgr.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }
}