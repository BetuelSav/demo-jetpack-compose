package com.example.hungrywolfscompose.application

import android.app.Application
import com.example.hungrywolfscompose.shared.utils.NetworkUtil
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(applicationContext).build()
        startKoin{
            androidContext(this@ApplicationClass)
            modules(AppModules.modules)
        }
        NetworkUtil.startNetworkCallback(this)
    }

    override fun onTerminate() {
        NetworkUtil.stopNetworkCallback(this)
        super.onTerminate()
    }
}