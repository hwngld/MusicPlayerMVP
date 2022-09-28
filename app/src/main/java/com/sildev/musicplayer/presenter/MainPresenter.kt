package com.sildev.musicplayer.presenter

import android.content.Context
import com.sildev.musicplayer.MusicPlayerHelper
import com.sildev.musicplayer.datalocal.DataManager

class MainPresenter(private val mainInterface: MainInterface) {

    fun showOrHideMiniPlayer() {
        if (getPositionSong() >= 0) {
            mainInterface.showMiniPlayer()
        } else {
            mainInterface.hideMiniPlayer()
        }
    }

    fun setPlaying(isPlaying: Boolean) {
        DataManager.setPlaying(isPlaying)
    }

    private fun getPositionSong(): Int {
        return DataManager.getPosition()
    }

    fun loadDataToSongList(context: Context) {
        val songList = MusicPlayerHelper.fetchSongFromStorage(context)
        mainInterface.setDataToSongList(songList)
    }

    fun setRepeat() {
        val isRepeat = !isRepeat()
        DataManager.setRepeat(isRepeat)
    }

    fun isRepeat(): Boolean {
        return DataManager.getRepeat()

    }

    fun setShuffle() {
        val isShuffle = !isShuffle()
        DataManager.setShuffle(isShuffle)
    }

    fun isShuffle(): Boolean {
        return DataManager.getShuffle()
    }

    fun setPositionSong(position: Int) {
        DataManager.setPosition(position)
    }

}