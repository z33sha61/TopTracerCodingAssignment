package com.example.myapplication.helper

import android.app.ProgressDialog
import android.content.Context

var loadingDialog: ProgressDialog? = null

fun Context.showLoader() {
    loadingDialog = ProgressDialog(this)
    loadingDialog?.setMessage("Loading...")
    loadingDialog?.show()
}

fun cancelDialog() {
    loadingDialog?.dismiss()
}