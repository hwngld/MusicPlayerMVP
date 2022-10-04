package com.sildev.musicplayer.repository.local

import android.content.Context
import android.widget.Toast
import com.sildev.musicplayer.MusicPlayerHelper
import com.sildev.musicplayer.model.Song
import com.sildev.musicplayer.repository.source.MusicDataSource

class MusicLocalDataSource : MusicDataSource.Local {
    override fun getMusicsLocal(context: Context, listener: OnResultListener<MutableList<Song>>) {
        val songList = MusicPlayerHelper.fetchSongFromStorage(context)
        if (songList.size > 0) {
            listener.onSuccess(songList)
        } else {
            listener.onError(Exception())
        }
    }

    companion object {
        private var instance: MusicLocalDataSource? = null

        fun getInstance() = synchronized(this) {
            instance ?: MusicLocalDataSource().also { instance = it }
        }
    }

}
