package com.sinlov.kotlin.android.demo

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions

/**
 * Created by sinlov on 2021/5/6.
 */
@SuppressLint("StaticFieldLeak")
class PermissionViewModule(
        private val context: Context
) : ViewModel() {

    private lateinit var permissionCallback: OnPermissionCallback

    fun setPermissionCallback(callback: OnPermissionCallback?) {
        this.permissionCallback = callback!!
    }

    fun requestFullPermission() {
        XXPermissions.with(context)
                //.permission(Permission.MANAGE_EXTERNAL_STORAGE)
                .permission(Permission.READ_EXTERNAL_STORAGE)
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .request(this.permissionCallback)
    }

    fun startSelfApplicationDetails() {
        XXPermissions.startApplicationDetails(context)
    }
}