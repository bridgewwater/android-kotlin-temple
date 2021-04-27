package com.sinlov.kotlin.android.plugin

/**
 * this is test of Plugin singleton object, you can change this!
 */
class Plugin private constructor() {
    companion object {
        /**
         * lazy @kotlin.internal.InlineOnly
         */
        val instance: Plugin by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Plugin()
        }
    }
    fun doBiz(): String {
        return "Plugin do biz"
    }
}
