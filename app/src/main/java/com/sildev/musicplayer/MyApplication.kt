package com.sildev.musicplayer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.sildev.musicplayer.datalocal.DataManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataManager.initManager(applicationContext)
    }

}
