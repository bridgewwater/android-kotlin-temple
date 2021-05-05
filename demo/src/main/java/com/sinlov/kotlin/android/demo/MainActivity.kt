package com.sinlov.kotlin.android.demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.sinlov.kotlin.android.demo.databinding.ActivityMainBinding
import com.sinlov.kotlin.android.demo.databinding.MainProfileBinding
import com.sinlov.temp.android.kts.AbsTemplateActivity
import com.sinlov.temp.android.kts.utils.clipboardUtilsCopy2clipboard
import com.sinlov.temp.android.kts.utils.clipboardUtilsItem
import com.sinlov.temp.android.kts.viewmodel.CtxViewModelFactory

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
        val mainProfile = rootView.mainProfile
        mainProfile.btnInitCheck.setOnClickListener {
            toastBottom("module init check")
        }
        mainProfile.btnSkipToModule.setOnClickListener {
//            toast("Skip to module: ${Plugin.instance.doBiz()}")
            toast("Skip to module")
        }
        mainProfile.tvResult.setOnClickListener {
            clipboardUtilsCopy2clipboard(this, mainProfile.tvResult.text.toString())
            toast("copy at Clipboard")
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        val b = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        b.lifecycleOwner = this

        val appInfo = ViewModelProvider(this, CtxViewModelFactory(this)).get(AppInfo::class.java)
        b.appInfo = appInfo

        val permissionVM = ViewModelProvider(this, CtxViewModelFactory(this)).get(PermissionViewModule::class.java)
        permissionVM.setPermissionCallback(NeedPermissionCall(this))
        b.permission = permissionVM

    }

    override fun onResume() {
        super.onResume()
        val clipboardUtils = clipboardUtilsItem(this, 0)
        Log.d(TAG, "onResume: clipboardUtilsLabel: $clipboardUtils")
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