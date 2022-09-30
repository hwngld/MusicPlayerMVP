package com.sildev.musicplayer.presenter

import android.content.Context
import com.sildev.musicplayer.model.Song

interface MainContract {
    interface Presenter {
        fun showOrHideMiniPlayer()
        fun setPlaying(isPlaying: Boolean)
        fun getPositionSong(): Int
        fun loadDataToSongList(context: Context)
        fun setRepeat()
        fun isRepeat(): Boolean
        fun setShuffle()
        fun isShuffle(): Boolean
        fun setPositionSong(position: Int)
    }

    interface View {
        fun showMiniPlayer()
        fun hideMiniPlayer()
        fun showListSong(list: MutableList<Song>)
    }

}
