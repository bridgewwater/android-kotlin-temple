package com.sinlov.temp.android.kts.action

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle

/**
 * Activity 意图
 * <pre>
 *     sinlov
 *
 *     /\__/\
 *    /`    '\
 *  ≈≈≈ 0  0 ≈≈≈ Hello world!
 *    \  --  /
 *   /        \
 *  /          \
 * |            |
 *  \  ||  ||  /
 *   \_oo__oo_/≡≡≡≡≡≡≡≡o
 *
 * </pre>
 * Created by sinlov on 2021/4/26.
 */
interface TempActivityAction {

    fun getContext(): Context?

    @JvmDefault
    fun getActivity(): Activity? {
        var context = getContext()
        do {
            context = if (context is Activity) {
                return context
            } else if (context is ContextWrapper) {
                context.baseContext
            } else {
                return null
            }
        } while (context != null)
        return null
    }


    @JvmDefault
    fun skip2Activity(clazz: Class<out Activity?>?) {
        getContext()?.startActivity(Intent(getContext(), clazz))
    }

    /**
     * 带参数跳转
     *
     * @param clazz  [Activity]
     * @param bundle [Bundle]
     */
    @JvmDefault
    fun skip2Activity(clazz: Class<out Activity?>?, bundle: Bundle?) {
        val intent = Intent(getContext(), clazz)
        if (null != bundle) {
            intent.putExtras(bundle)
        }
        getContext()?.startActivity(intent)
    }

    @JvmDefault
    fun skipActivity(intent: Intent) {
        if (getContext() !is Activity) {
            // 如果当前的上下文不是 Activity，调用 startActivity 必须加入新任务栈的标记，否则会报错：android.util.AndroidRuntimeException
            // Calling startActivity() from outside of an Activity context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        getContext()!!.startActivity(intent)
    }
}