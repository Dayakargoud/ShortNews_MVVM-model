package com.dayakar.shortnews

import android.app.Application
import com.dayakar.shortnews.network.NetworkConnection

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        //This our implemeted class forchecking  internet connection
       // NetworkConnection(this)
    }
}