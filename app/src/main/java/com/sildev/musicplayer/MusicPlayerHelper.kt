package com.sildev.musicplayer

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
    fun fetchSongFromStorage(context: Context): MutableList<Song> {
        val songList = mutableListOf<Song>()
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
                    val audioArtist =
                        audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                    val audioDuration =
                        audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                    val audioData = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                    val albumId = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
                    val album = audioCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
                    val duration = audioCursor.getString(audioDuration)
                    if (duration != null) {
                        if (duration.toInt() > 15000) {
                            val song = Song(audioCursor.getLong(audioId))
                            song.name = audioCursor.getString(audioTitle)
                            song.singer = audioCursor.getString(audioArtist)
                            song.path = audioCursor.getString(audioData)
                            song.duration = duration.toInt()
                            song.albumId = audioCursor.getLong(albumId)
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
        return Pattern.compile(REGEX_ACCENT).matcher(temp).replaceAll("")
            .lowercase(Locale.getDefault())
    }

    fun getBitmapSong(path: String): Bitmap? {
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(Uri.parse(path).toString())
        val byteImage = mmr.embeddedPicture
        if (byteImage != null) {
            return BitmapFactory.decodeByteArray(byteImage, 0, byteImage.size)
        }
        return null
    }
}
