package com.sinlov.kotlin.android.demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.sinlov.kotlin.android.demo.databinding.ActivityMainBinding
import com.sinlov.kotlin.android.demo.model.AppInfo
import com.sinlov.kotlin.android.demo.model.CommonBiz
import com.sinlov.kotlin.android.demo.model.OnSkipAction
import com.sinlov.kotlin.android.demo.model.PermissionViewModule
import com.sinlov.temp.android.kts.AbsTemplateActivity
import com.sinlov.temp.android.kts.utils.ClipboardUtils
import com.sinlov.temp.android.kts.viewmodelfact.CtxViewModelFactory
import timber.log.Timber

/**
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
class MainActivity : AbsTemplateActivity() {

    private lateinit var rootView: ActivityMainBinding

    override fun onSetContentView(): View? {
        rootView = ActivityMainBinding.inflate(layoutInflater)
        return rootView.root
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        val b = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        b.lifecycleOwner = this

        val appInfo = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(this.application))
                .get(AppInfo::class.java)
        b.appInfo = appInfo

        val permissionVM = ViewModelProvider(this, CtxViewModelFactory(this))
                .get(PermissionViewModule::class.java)
        permissionVM.setPermissionCallback(NeedPermissionCall(this))
        b.permission = permissionVM

        val commonBiz = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(this.application))
                .get(CommonBiz::class.java)
        commonBiz.onSkipAction(SkipAction())
        b.commonBiz = commonBiz
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun onResume() {
        super.onResume()
        val clipboardUtils = ClipboardUtils.item(this, 0)
        Log.d(TAG, "onResume: clipboardUtilsLabel: $clipboardUtils")
    }

    class SkipAction : OnSkipAction {
        override fun onSkipTestModule() {
            super.onSkipTestModule()
            Timber.d("this to skip test action")
        }
    }

    private class NeedPermissionCall(
            private var mainActivity: MainActivity
    ) : OnPermissionCallback {
        override fun onGranted(permissions: List<String>, all: Boolean) {
            if (all) {
                mainActivity.toast("all permissions granted")
            } else {
                mainActivity.toast("some permission denied")
//                activty.requestFullPermission()
            }
        }

        override fun onDenied(permissions: List<String>, never: Boolean) {
            if (never) {
                mainActivity.toast("permissions never granted")
                // 如果是被永久拒绝就跳转到应用权限系统设置页面
                XXPermissions.startPermissionActivity(mainActivity, permissions)
            } else {
                mainActivity.toast("some permission granted fail")
            }
        }
    }
}