package com.sildev.musicplayer.presenter

import android.content.Context
import com.sildev.musicplayer.MusicPlayerHelper
import com.sildev.musicplayer.datalocal.DataManager

class MainPresenter(private val mainView: MainContract.View) : MainContract.Presenter {

    override fun showOrHideMiniPlayer() {
        if (getPositionSong() >= 0) {
            mainView.showMiniPlayer()
        } else {
            mainView.hideMiniPlayer()
        }
    }

    override fun setPlaying(isPlaying: Boolean) {
        DataManager.setPlaying(isPlaying)
    }

    override fun getPositionSong(): Int {
        return DataManager.getPosition()
    }

    override fun loadDataToSongList(context: Context) {
        val songList = MusicPlayerHelper.fetchSongFromStorage(context)
        mainView.showListSong(songList)
    }

    override fun setRepeat() {
        val isRepeat = !isRepeat()
        DataManager.setRepeat(isRepeat)
    }

    override fun isRepeat(): Boolean {
        return DataManager.getRepeat()

    }

    override fun setShuffle() {
        val isShuffle = !isShuffle()
        DataManager.setShuffle(isShuffle)
    }

    override fun isShuffle(): Boolean {
        return DataManager.getShuffle()
    }

    override fun setPositionSong(position: Int) {
        DataManager.setPosition(position)
    }

}
