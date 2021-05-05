package com.sinlov.kotlin.android.demo

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sinlov.temp.android.kts.system.PMSUtil

/**
 * Created by sinlov on 2021/5/6.
 */
@SuppressLint("StaticFieldLeak")
class AppInfo(private val context: Context) : ViewModel() {

    private val _name = MutableLiveData<String>()

    private val _debug = MutableLiveData<Boolean>()

    val name: LiveData<String>
        get() {
            _name.value = PMSUtil.selfAppName(context)
            return _name
        }

    val debug: LiveData<Boolean>
        get() {
            _debug.value = com.sinlov.kotlin.android.demo.BuildConfig.DEBUG
            return _debug
        }

    fun baseInfo(): String {
        return "Name: ${name.value}, run ${if (this.debug.value == true) "mode DEBUG" else "release"}"
    }
}