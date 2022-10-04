package com.sildev.musicplayer

import android.app.Application
import com.sildev.musicplayer.datalocal.DataManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataManager.initManager(applicationContext)
    }

}
