package com.platform_science.routing_shipments

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.platform_science.routing_shipments.common.Utils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PSCodeApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        var utils: Utils? = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        utils = Utils(applicationContext)
    }
}