package com.example.weather

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.provider.Settings.Global.getString
import com.example.weather.R.*
import com.example.weather.R.string.*

object DialogManager {
    fun locationSettingsDialog(context: Context, listener: Listener) {
        val builder = AlertDialog.Builder(context)
        val dialog = builder.create()
        dialog.setTitle(title)
        dialog.setMessage(com.example.weather.All.Application2.applicationContext().resources.getString(R.string.message))
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            listener.onClick(1)
            dialog.dismiss()
        }

        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, com.example.weather.All.Application2.applicationContext().resources.getString(R.string.cancel)) { _, _ ->
            dialog.dismiss()
            listener.onClick(2)

        }
        dialog.show()
    }

    interface Listener{
        fun onClick(i: Int)
    }
}


