package com.sildev.musicplayer.repository.source

import android.content.Context
import android.widget.Toast
import com.sildev.musicplayer.MusicPlayerHelper.fetchSongFromStorage
import com.sildev.musicplayer.model.Song
import com.sildev.musicplayer.repository.local.OnResultListener

interface MusicDataSource {
    interface Local {
        fun getMusicsLocal(context: Context, listener: OnResultListener<MutableList<Song>>)
    }
}
