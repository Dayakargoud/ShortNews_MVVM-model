package com.dayakar.shortnews.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NetworkConnection(app: Context) : LiveData<Boolean>() {
   private var Internet=MutableLiveData<Boolean>()
    val internet:LiveData<Boolean> get() = Internet
    private lateinit var appliction: Context
    private lateinit var networkRequet: NetworkRequest



    init {
        this.appliction = app
        networkRequet = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }

    override fun onActive() {
        super.onActive()
        getNetworkDetails()
    }

    fun getNetworkDetails() {
        val connectivityManager = appliction.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequet,
            object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(true)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    postValue(false)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    postValue(false)
                }
            })

    }

}