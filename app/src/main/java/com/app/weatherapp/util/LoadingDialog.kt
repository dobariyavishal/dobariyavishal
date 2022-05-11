package com.app.weatherapp.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.app.weatherapp.databinding.DialogLoadingBinding
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.RuntimeException

object LoadingDialog {
    var dialog: Dialog? = null

    fun showLoadDialog(context: Context) {
        if (dialog != null && dialog?.isShowing == true)
            return

        dialog = Dialog(context)
        dialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setCancelable(false)
            val binding = DialogLoadingBinding.inflate(LayoutInflater.from(context))
            setContentView(binding.root)
            show()
        }
    }

    fun hideLoadDialog() {
        try {
            if (dialog != null) {
                if (dialog?.isShowing == true) {
                    dialog?.dismiss()
                }
            }
            dialog = null
        } catch (ie: IllegalArgumentException) {
            ie.printStackTrace()
        } catch (re: RuntimeException) {
            re.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}