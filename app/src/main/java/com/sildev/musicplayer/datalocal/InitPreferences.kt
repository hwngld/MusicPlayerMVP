package com.sildev.musicplayer.datalocal

import android.content.Context
import com.sildev.musicplayer.MY_PREFERENCES_NAME

class InitPreferences(val context: Context) {

    fun setInt(key: String?, value: Int) {
        val sharedPreferences =
            context.getSharedPreferences(MY_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.putInt(key, value)
        editor.commit()
    }

    fun getInt(key: String?): Int {
        val sharedPreferences =
            context.getSharedPreferences(MY_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, -1)
    }


    fun putBoolean(key: String?, value: Boolean) {
        val sharedPreferences =
            context.getSharedPreferences(MY_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }


    fun getBoolean(key: String?): Boolean {
        val sharedPreferences =
            context.getSharedPreferences(MY_PREFERENCES_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }
}