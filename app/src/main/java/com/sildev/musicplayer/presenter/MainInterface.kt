package com.sildev.musicplayer.presenter

import com.sildev.musicplayer.model.Song

interface MainInterface {
    fun showMiniPlayer()
    fun hideMiniPlayer()
    fun setDataToSongList(list: List<Song>)

}