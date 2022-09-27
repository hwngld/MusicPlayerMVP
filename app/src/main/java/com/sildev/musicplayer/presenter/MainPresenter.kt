package com.sildev.musicplayer.presenter

import android.content.Context
import com.sildev.musicplayer.MusicPlayerHelper
import com.sildev.musicplayer.datalocal.DataManager

class MainPresenter(private val mainInterface: MainInterface) {

    fun showOrHideMiniPlayer() {
        if (getPositionSong()!! >= 0) {
            mainInterface.showMiniPlayer()
        } else {
            mainInterface.hideMiniPlayer()
        }
    }

    fun setIsPlaying(isPlaying: Boolean) {
        DataManager.setIsPlaying(isPlaying)
    }

    private fun getPositionSong(): Int? {
        return DataManager.getPosition()
    }

    fun loadDataToSongList(context: Context) {
        val songList = MusicPlayerHelper.fetchSongFromStorage(context)
        mainInterface.setDataToSongList(songList)
    }

    fun setIsRepeat() {
        val isRepeat = getIsRepeat()
        DataManager.setRepeat(!isRepeat!!)
    }

    fun getIsRepeat(): Boolean? {
        return DataManager.getRepeat()

    }

    fun setIsShuffle() {
        val isShuffle = getIsShuffle()
        DataManager.setShuffle(!isShuffle!!)
    }

    fun getIsShuffle(): Boolean? {
        return DataManager.getShuffle()

    }

}