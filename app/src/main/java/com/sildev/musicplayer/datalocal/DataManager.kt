package com.sildev.musicplayer.datalocal

import android.annotation.SuppressLint
import android.content.Context
import com.sildev.musicplayer.IS_PLAYING_KEY
import com.sildev.musicplayer.IS_REPEAT_KEY
import com.sildev.musicplayer.IS_SHUFFLE_KEY
import com.sildev.musicplayer.POSITION_SONG_KEY

object DataManager {
    private var instance: DataManager? = null

    @SuppressLint("StaticFieldLeak")
    private var initPreferences: InitPreferences? = null

    fun initManager(context: Context) {
        instance = DataManager
        instance!!.initPreferences = InitPreferences(context)
    }

    private fun getInstance(): DataManager? {
        if (instance == null) {
            instance = DataManager
        }
        return instance
    }

    fun setPosition(position: Int) {
        DataManager.getInstance()?.initPreferences?.setInt(POSITION_SONG_KEY, position)
    }

    fun getPosition(): Int? {
        return DataManager.getInstance()?.initPreferences?.getInt(POSITION_SONG_KEY)
    }


    fun setShuffle(isShuffle: Boolean) {
        DataManager.getInstance()?.initPreferences?.putBoolean(IS_SHUFFLE_KEY, isShuffle)
    }

    fun getShuffle(): Boolean? {
        return DataManager.getInstance()?.initPreferences?.getBoolean(IS_SHUFFLE_KEY)
    }

    fun setRepeat(isShuffle: Boolean) {
        DataManager.getInstance()?.initPreferences?.putBoolean(IS_REPEAT_KEY, isShuffle)
    }

    fun getRepeat(): Boolean? {
        return DataManager.getInstance()?.initPreferences?.getBoolean(IS_REPEAT_KEY)
    }

    fun setIsPlaying(isPlaying: Boolean) {
        DataManager.getInstance()?.initPreferences?.putBoolean(IS_PLAYING_KEY, isPlaying)
    }

    fun getIsPlaying(): Boolean? {
        return DataManager.getInstance()?.initPreferences?.getBoolean(IS_PLAYING_KEY)
    }


}