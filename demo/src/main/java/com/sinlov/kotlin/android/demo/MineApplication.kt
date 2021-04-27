package com.sinlov.kotlin.android.demo

import android.app.Application
import android.content.Context
import com.hjq.toast.ToastUtils

class MineApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        initSDK(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    private fun initSDK(application: Application) {
        ToastUtils.init(application)
    }
}