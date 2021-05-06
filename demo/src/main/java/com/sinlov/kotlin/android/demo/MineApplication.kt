package com.sinlov.kotlin.android.demo

import android.app.Application
import android.content.Context
import com.hjq.toast.ToastUtils
import com.sinlov.kotlin.android.demo.log.LoggerPrintTree
import com.sinlov.temp.android.kts.system.AMLUtil
import com.sinlov.temp.android.kts.system.PMSUtil
import timber.log.Timber

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
        // 按需日志打印
        if (PMSUtil.selfDebugEnable(application)) {
            Timber.plant(LoggerPrintTree())
            Timber.tag("MineApplication")
            Timber.d("just open log print")
        }
        AMLUtil.INSTANCE.init(this)
                .setDebug(true)
        ToastUtils.init(application)
    }
}