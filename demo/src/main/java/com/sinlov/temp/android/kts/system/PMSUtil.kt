package com.sinlov.temp.android.kts.system

import android.content.Context
import android.content.pm.ApplicationInfo

/**
 * PMS Util
 * Created by sinlov on 2021/5/6.
 */
class PMSUtil {

    companion object {

        @Volatile
        private var hasSelfDebugCheck = false

        @Volatile
        private var selfDebugCache = false

        fun selfDebugEnable(ctx: Context): Boolean {
            if (hasSelfDebugCheck) {
                return selfDebugCache
            }
            synchronized(PMSUtil::class.java) {
                try {
                    val info = ctx.applicationInfo
                    selfDebugCache = info.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
                    hasSelfDebugCheck = true
                    return selfDebugCache
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            return false
        }

        fun selfAppName(ctx: Context): String {
            if (ctx.packageManager != null) {
                val packageInfo = ctx.packageManager.getPackageInfo(ctx.packageName, 0)
                return ctx.resources.getString(packageInfo.applicationInfo.labelRes)
            }
            return ""
        }
    }
}