package com.sinlov.kotlin.android.demo.log

import android.os.Build
import timber.log.Timber

/**
 * </pre>
 * Created by sinlov on 2021/5/6.
 */
class LoggerPrintTree : Timber.DebugTree() {

    /**
     * TAG 长度限制 Android 7.0 之前
     */
    private val MAX_TAG_LENGTH = 23

    override fun createStackElementTag(element: StackTraceElement): String? {
        val tag = "(" + element.fileName + ":" + element.lineNumber + ")"
        return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tag
        } else tag.substring(0, MAX_TAG_LENGTH)
    }
}