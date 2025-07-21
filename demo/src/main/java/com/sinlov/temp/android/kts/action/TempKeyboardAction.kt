package com.sinlov.temp.android.kts.action

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * 软键盘意图
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
interface TempKeyboardAction {

    /**
     * 显示软键盘
     */
    @JvmDefault
    fun showKeyboard(view: View?) {
        if (view == null) {
            NullPointerException("showKeyboard as Null View").printStackTrace()
            return
        }
        val manager = view.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(view, 0)
    }

    /**
     * 隐藏软键盘
     */
    @JvmDefault
    fun hideKeyboard(view: View?) {
        if (view == null) {
//            NullPointerException("hideKeyboard as Null View").printStackTrace()
            return
        }
        val manager = view.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**
     * 切换软键盘
     */
    @JvmDefault
    fun toggleSoftInput(view: View?) {
        if (view == null) {
            NullPointerException("toggleSoftInput as Null View").printStackTrace()
            return
        }
        val manager = view.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.toggleSoftInput(0, 0)
    }
}