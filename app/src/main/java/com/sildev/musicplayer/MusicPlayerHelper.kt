package com.sildev.musicplayer

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import com.sildev.musicplayer.model.Song
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


object MusicPlayerHelper {
    @SuppressLint("Recycle")
    fun fetchSongFromStorage(context: Context): List<Song> {
        val songList: MutableList<Song> = ArrayList()
        val proj = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID
        )
        val audioCursor = context.contentResolver
            .query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, null, null, null
            )
        if (audioCursor != null) {
            if (audioCursor.moveToFirst()) {
                do {
                    val audioId = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                    val audioTitle = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                    val audioartist =
                        audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                    val audioduration =
                        audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                    val audiodata = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                    val albumid = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
                    val album = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
                    var duration = 0
                    if (audioCursor.getString(audioduration) != null) {
                        duration = audioCursor.getString(audioduration).toInt()
                        if (duration > 15000) {
                            val song = Song()
                            song.id = audioCursor.getLong(audioId)
                            song.name = audioCursor.getString(audioTitle)
                            song.singer = audioCursor.getString(audioartist)
                            song.path = audioCursor.getString(audiodata)
                            song.duration = duration
                            song.albumId = audioCursor.getLong(albumid)
                            song.album = audioCursor.getString(album)
                            songList.add(song)
                        }

                    }
                } while (audioCursor.moveToNext())

            }
        }
        return songList
    }

    fun parseLongToTime(timeLong: Int): String {
        var time = ""
        time = SimpleDateFormat("mm:ss").format(Date(timeLong.toLong()))
        return time

    }

    fun removeAccent(s: String): String {
        val temp = Normalizer.normalize(s, Normalizer.Form.NFD)
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        return pattern.matcher(temp).replaceAll("").lowercase(Locale.getDefault())
    }

    fun getBitmapSong(path: String): Bitmap {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(Uri.parse(path).toString())
        val byteImage = mmr.embeddedPicture
        return BitmapFactory.decodeByteArray(byteImage, 0, byteImage!!.size)
    }
}