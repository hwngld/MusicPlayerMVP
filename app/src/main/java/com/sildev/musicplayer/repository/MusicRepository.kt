package com.sildev.musicplayer.repository

import android.content.Context
import com.sildev.musicplayer.model.Song
import com.sildev.musicplayer.repository.local.OnResultListener
import com.sildev.musicplayer.repository.source.MusicDataSource

class MusicRepository private constructor(private val local: MusicDataSource.Local) :
    MusicDataSource.Local {
    override fun getMusicsLocal(context: Context, listener: OnResultListener<MutableList<Song>>) {
        local.getMusicsLocal(context,listener)
    }

    companion object {
        private var instance: MusicRepository? = null

        fun getInstance(local: MusicDataSource.Local) = synchronized(this) {
            instance ?: MusicRepository(local).also { instance = it }
        }
    }
}
