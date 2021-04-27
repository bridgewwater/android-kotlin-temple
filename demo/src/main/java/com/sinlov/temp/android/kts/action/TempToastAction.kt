package com.sinlov.temp.android.kts.action

import android.view.Gravity
import androidx.annotation.StringRes
import com.hjq.toast.ToastUtils

/**
 * toast 意图
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
interface TempToastAction {
    @JvmDefault
    fun toast(text: CharSequence?) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.show(text)
    }

    @JvmDefault
    fun toast(@StringRes id: Int) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.show(id)
    }

    @JvmDefault
    fun toast(`object`: Any?) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.show(`object`)
    }

    @JvmDefault
    fun toastBottom(@StringRes id: Int) {
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 64)
        ToastUtils.show(id)
    }

    @JvmDefault
    fun toastBottom(text: CharSequence?) {
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 64)
        ToastUtils.show(text)
    }

    @JvmDefault
    fun toastBottom(`object`: Any?) {
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 64)
        ToastUtils.show(`object`)
    }
}