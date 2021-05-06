package com.sinlov.kotlin.android.demo.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sinlov.temp.android.kts.system.PMSUtil

/**
 * Created by sinlov on 2021/5/6.
 */
class AppInfo(
        application: Application
) : AndroidViewModel(application) {

    private val _name = MutableLiveData<String>()

    private val _debug = MutableLiveData<Boolean>()

    val name: LiveData<String>
        get() {
            _name.value = PMSUtil.selfAppName(getApplication())
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