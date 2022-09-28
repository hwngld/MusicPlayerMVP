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
        getInstance()?.initPreferences?.setInt(POSITION_SONG_KEY, position)
    }

    fun getPosition(): Int {
        val position = getInstance()?.initPreferences?.getInt(POSITION_SONG_KEY)
        if (position != null) {
            return position
        }
        return -1
    }


    fun setShuffle(isShuffle: Boolean) {
        getInstance()?.initPreferences?.putBoolean(IS_SHUFFLE_KEY, isShuffle)
    }

    fun getShuffle(): Boolean {
        val shuffle = getInstance()?.initPreferences?.getBoolean(IS_SHUFFLE_KEY)
        if (shuffle != null) {
            return shuffle
        }
        return false
    }

    fun setRepeat(isShuffle: Boolean) {
        getInstance()?.initPreferences?.putBoolean(IS_REPEAT_KEY, isShuffle)
    }

    fun getRepeat(): Boolean {
        val repeat = getInstance()?.initPreferences?.getBoolean(IS_REPEAT_KEY)
        if (repeat != null) {
            return repeat
        }
        return false
    }

    fun setPlaying(isPlaying: Boolean) {
        getInstance()?.initPreferences?.putBoolean(IS_PLAYING_KEY, isPlaying)
    }

    fun isPlaying(): Boolean {
        val playing = getInstance()?.initPreferences?.getBoolean(IS_PLAYING_KEY)
        if (playing != null) {
            return playing
        }
        return false
    }


}