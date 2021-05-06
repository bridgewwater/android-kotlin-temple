package com.sinlov.kotlin.android.demo.model

import android.app.Application
import android.view.View
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import com.sinlov.temp.android.kts.action.TempToastAction
import com.sinlov.temp.android.kts.utils.ClipboardUtils

/**
 * Created by sinlov on 2021/5/6.
 */
class CommonBiz(
        application: Application
) : AndroidViewModel(application), TempToastAction {

    private var onSkipAction: OnSkipAction? = null

    fun initCheck() {
        toast("init check")
    }

    fun tvCopy2Clipboard(view: View) {
        if (view is TextView) {
            ClipboardUtils.copy2clipboard(getApplication(), view.text.toString())
            toast("copy at Clipboard")
        }
    }

    fun onSkipAction(call: OnSkipAction) {
        this.onSkipAction = call
    }

    fun skipToTestModule() {
        toast("skip to test module")
        if (this.onSkipAction != null) {
            this.onSkipAction!!.onSkipTestModule()
        }
    }
}

