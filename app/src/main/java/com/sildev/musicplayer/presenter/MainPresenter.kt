package com.sildev.musicplayer.presenter

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.sildev.musicplayer.MusicPlayerHelper
import com.sildev.musicplayer.datalocal.DataManager
import com.sildev.musicplayer.model.Song
import com.sildev.musicplayer.repository.MusicRepository
import com.sildev.musicplayer.repository.local.OnResultListener

class MainPresenter(
    private val mainView: MainContract.View, private val musicRepository: MusicRepository
) : MainContract.Presenter {

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
        musicRepository.getMusicsLocal(context, object : OnResultListener<MutableList<Song>> {
            override fun onSuccess(data: MutableList<Song>) {
                mainView.showListSong(data)
            }

            override fun onError(exception: Exception?) {
                Log.e("errorLoadDataToSongList", exception?.message.toString())
            }
        })

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
