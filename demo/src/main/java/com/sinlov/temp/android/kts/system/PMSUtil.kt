package com.sinlov.temp.android.kts.system

import android.content.Context

/**
 * PMS Util
 * Created by sinlov on 2021/5/6.
 */
class PMSUtil {
    companion object {
        fun selfAppName(ctx: Context): String {
            if (ctx.packageManager != null) {
                val packageInfo = ctx.packageManager.getPackageInfo(ctx.packageName, 0)
                return ctx.resources.getString(packageInfo.applicationInfo.labelRes)
            }
            return ""
        }
    }
}