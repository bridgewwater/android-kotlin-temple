package com.sinlov.kotlin.android.demo

import android.os.Bundle
import android.util.Log
import android.view.View
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.sinlov.temp.android.kts.AbsTemplateActivity
import com.sinlov.temp.android.kts.utils.clipboardUtilsCopy2clipboard
import com.sinlov.kotlin.android.demo.databinding.ActivityMainBinding
import com.sinlov.temp.android.kts.utils.clipboardUtilsItem

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

    override fun initView() {
        rootView.mainProfile.btnInitCheck.setOnClickListener {
            toastBottom("module init check")
        }
        rootView.mainProfile.btnSkipToModule.setOnClickListener {
//            toast("Skip to module: ${Plugin.instance.doBiz()}")
            toast("Skip to module")
        }
        rootView.mainProfile.btnSkipAppDetails.setOnClickListener {
            XXPermissions.startApplicationDetails(this)
        }
        rootView.mainProfile.btnGrantPermission.setOnClickListener {
            requestFullPermission()
        }
        rootView.mainProfile.tvResult.setOnClickListener {
            clipboardUtilsCopy2clipboard(this, rootView.mainProfile.tvResult.text.toString())
            toast("copy at Clipboard")
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun onResume() {
        super.onResume()
        val clipboardUtils = clipboardUtilsItem(this, 0)
        Log.d(TAG, "onResume: clipboardUtilsLabel: $clipboardUtils")
    }

    private fun requestFullPermission() {
        XXPermissions.with(this)
                //                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                .permission(Permission.READ_EXTERNAL_STORAGE)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .request(NeedPermissionCall(this))
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