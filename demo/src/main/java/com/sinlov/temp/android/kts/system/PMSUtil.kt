package com.sinlov.temp.android.kts.system

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

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

        /**
         * check out self is debug
         *
         * @param ctx [Context]
         * @return AndroidManifest.xml debuggable
         */
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

        /**
         * get self version code
         *
         * @param ctx [Context]
         * @return as AndroidManifest.xml application node android:versionCode
         */
        fun selfVersionCode(ctx: Context): Int {
            var versionCode = 0
            try {
                versionCode = ctx.packageManager.getPackageInfo(ctx.packageName, 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return versionCode
        }

        /**
         * get self version name
         *
         * @param ctx [Context]
         * @return as AndroidManifest.xml application node android:versionName
         */
        fun selfVersionName(ctx: Context): String? {
            var verName: String? = ""
            try {
                verName = ctx.packageManager.getPackageInfo(ctx.packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return verName
        }

        /**
         * get self label string
         *
         * @param ctx [Context]
         * @return as AndroidManifest.xml application node android:label
         */
        fun selfAppName(ctx: Context): String {
            if (ctx.packageManager != null) {
                val packageInfo = ctx.packageManager.getPackageInfo(ctx.packageName, 0)
                return ctx.resources.getString(packageInfo.applicationInfo.labelRes)
            }
            return ""
        }
    }
}