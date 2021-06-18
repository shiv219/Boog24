package com.boog24.util

import android.app.AlertDialog
import android.content.Context
import com.boog24.R

object DialogUtils {

    @JvmStatic
    fun showMszDialog(context: Context, msz: String) {

        AlertDialog.Builder(context)
                .setMessage(msz)
                .setPositiveButton(R.string.ok) { dialog, which ->
                    dialog.dismiss()
                }.create().show()
    }

    fun test() {

        val arr = Array(5) { i -> Array(5) { j -> (5 * i) + j } }


    }

}