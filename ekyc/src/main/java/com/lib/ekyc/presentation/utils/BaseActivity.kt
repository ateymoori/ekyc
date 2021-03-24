package com.lib.ekyc.presentation.utils

import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

open class BaseActivity : AppCompatActivity() {
    open fun showMessage(msg: String?) {
        msg?.let {
            it.toast(this)
        }
    }

    open fun showError(msg: String?) {
        msg?.let {
            it.toast(this)
        }
    }

    fun checkPermission(permission: String, onGranted: (PERMISSION_RESULT) -> Unit) {
        Dexter.withContext(this)
            .withPermission(
                permission
            ).withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    onGranted.invoke(PERMISSION_RESULT.GRANTED)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    onGranted.invoke(PERMISSION_RESULT.DENIED)
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                    onGranted.invoke(PERMISSION_RESULT.RATIONAL)
                }

            }).check()
    }

}