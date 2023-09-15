package com.example.hungrywolfscompose.application

import android.app.Application
import com.example.hungrywolfscompose.shared.utils.NetworkUtil
import com.orhanobut.hawk.Hawk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(applicationContext).build()
        NetworkUtil.startNetworkCallback(this)
    }

    override fun onTerminate() {
        NetworkUtil.stopNetworkCallback(this)
        super.onTerminate()
    }
}