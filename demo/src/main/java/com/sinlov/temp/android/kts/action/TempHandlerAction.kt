package com.sinlov.temp.android.kts.action

import android.os.Handler
import android.os.Looper
import android.os.SystemClock

val MAIN_HANDLER = Handler(Looper.getMainLooper())
val HANDLER = Handler(Looper.myLooper()!!)

/**
 * Handler 意图处理
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
interface TempHandlerAction {
    @JvmDefault
    fun getMainHandler(): Handler {
        return MAIN_HANDLER
    }

    @JvmDefault
    fun getMyHandler(): Handler {
        return HANDLER
    }

    /**
     * 主线程延迟执行
     */
    @JvmDefault
    fun post(r: Runnable): Boolean {
        return postDelayed(r, 0)
    }

    /**
     * 线程延迟执行
     */
    @JvmDefault
    fun postMy(r: Runnable): Boolean {
        return postMyDelayed(r, 0)
    }

    /**
     * 主线程延迟一段时间执行
     */
    @JvmDefault
    fun postDelayed(r: Runnable, delayMillis: Long): Boolean {
        var ckDelayMillis = delayMillis
        if (ckDelayMillis < 0) {
            ckDelayMillis = 0
        }
        return postAtTime(r, SystemClock.uptimeMillis() + ckDelayMillis)
    }

    /**
     * 线程延迟一段时间执行
     */
    @JvmDefault
    fun postMyDelayed(r: Runnable, delayMillis: Long): Boolean {
        var ckDelayMillis = delayMillis
        if (ckDelayMillis < 0) {
            ckDelayMillis = 0
        }
        return postMyAtTime(r, SystemClock.uptimeMillis() + ckDelayMillis)
    }

    /**
     * 在主线程指定的时间执行
     */
    @JvmDefault
    fun postAtTime(r: Runnable, uptimeMillis: Long): Boolean {
        // 发送和当前对象相关的消息回调
        return MAIN_HANDLER.postAtTime(r, this, uptimeMillis)
    }

    /**
     * 在线程指定的时间执行
     */
    @JvmDefault
    fun postMyAtTime(r: Runnable, uptimeMillis: Long): Boolean {
        // 发送和当前对象相关的消息回调
        return HANDLER.postAtTime(r, this, uptimeMillis)
    }

    /**
     * 移除主线程单个消息回调
     */
    @JvmDefault
    fun removeCallbacks(r: Runnable?) {
        MAIN_HANDLER.removeCallbacks(r!!)
    }

    /**
     * 移出线程单个消息回调
     */
    @JvmDefault
    fun removeMyCallbacks(r: Runnable?) {
        HANDLER.removeCallbacks(r!!)
    }

    /**
     * 移除和当前对象相关的消息回调
     */
    @JvmDefault
    fun removeALLCallbacks() {
        MAIN_HANDLER.removeCallbacksAndMessages(this)
        HANDLER.removeCallbacksAndMessages(this)
    }
}