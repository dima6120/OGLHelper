package com.dima6120.demo

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

class PermissionUtils {
    companion object {
        val STORAGE_PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        fun verifyPermissions(grantResults: IntArray): Boolean {
            if (grantResults.isEmpty()) {
                return false
            }

            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }

            return true
        }

        fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                for (permission in permissions) {
                    if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                        return false
                    }
                }
            }

            return true
        }
    }
}