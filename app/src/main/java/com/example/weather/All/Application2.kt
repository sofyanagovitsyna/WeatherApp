package com.example.weather.All//import android.app.Application
import android.app.Application
import android.content.Context


class  Application2: Application()  {
    init {
        instance = this
    }

    companion object {
        private var instance: Application2? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any

        // Use ApplicationContext.
        // example: SharedPreferences etc...
        val context: Context = applicationContext()
    }
}