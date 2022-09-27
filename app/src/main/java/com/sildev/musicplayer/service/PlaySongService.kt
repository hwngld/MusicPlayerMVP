package com.sildev.musicplayer.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.media.MediaMetadata
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sildev.musicplayer.*
import com.sildev.musicplayer.MusicPlayerHelper.getBitmapSong
import com.sildev.musicplayer.activity.MainActivity
import com.sildev.musicplayer.datalocal.DataManager
import com.sildev.musicplayer.model.Song


class PlaySongService : Service() {
    private lateinit var currentSong: Song
    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            if (intent.action == ACTION_PLAY) {
                currentSong = intent.getSerializableExtra("currentSong") as Song
            }

            updateNotification()
            if (intent.action == ACTION_STOP) {
                stopSelf()
            }

        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent != null) {
            currentSong = intent.getSerializableExtra("currentSong") as Song
        }
        showNotification()

        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_PREVIOUS)
        intentFilter.addAction(ACTION_PLAY)
        intentFilter.addAction(ACTION_PAUSE)
        intentFilter.addAction(ACTION_NEXT)
        intentFilter.addAction(ACTION_STOP)
        registerReceiver(broadcastReceiver, intentFilter)

        return START_NOT_STICKY
    }

    private fun updateNotification() {
        val song: Song = currentSong
        val mBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, MUSIC_CHANNEL_ID)
        val mMediaSessionCompat = MediaSessionCompat(this, "12313")
        mMediaSessionCompat.setMetadata(
            MediaMetadataCompat.Builder().putString(MediaMetadata.METADATA_KEY_TITLE, song.name)
                .putString(MediaMetadata.METADATA_KEY_ARTIST, song.singer).build()
        )
        val iconPlayPause =
            if (DataManager.getIsPlaying() == true) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play
        mBuilder.addAction(
            android.R.drawable.ic_media_previous, "rw30", pendingIntentMusic(
                ACTION_PREVIOUS, PREVIOUS_INTENT_REQUEST_CODE
            )
        )
            .addAction(
                iconPlayPause, "Pause", pendingIntentMusic(
                    ACTION_PAUSE,
                    PAUSE_INTENT_REQUEST_CODE
                )
            )
            .addAction(
                android.R.drawable.ic_media_next,
                "ff30",
                pendingIntentMusic(ACTION_NEXT, NEXT_INTENT_REQUEST_CODE)
            )
            .addAction(
                android.R.drawable.ic_menu_close_clear_cancel, "Stop", pendingIntentMusic(
                    ACTION_STOP, STOP_INTENT_REQUEST_CODE
                )
            ).setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
                    .setMediaSession(mMediaSessionCompat.sessionToken)
            ).setContentTitle(song.name).setContentText(song.singer)
            .setSmallIcon(R.drawable.ic_music).setPriority(Notification.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntentResume())
        try {
            mBuilder.setLargeIcon(getBitmapSong(song.path))
        } catch (_: java.lang.Exception) {
            mBuilder.setLargeIcon(
                BitmapFactory.decodeResource(
                    Resources.getSystem(), R.drawable.ic_music
                )
            )
        }
        NotificationManagerCompat.from(this).notify(FOREGROUND_SERVICE_ID, mBuilder.build())
    }

    private fun showNotification() {
        val song: Song = currentSong
        val mBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, MUSIC_CHANNEL_ID)
        val mMediaSessionCompat = MediaSessionCompat(this, "12313")
        mMediaSessionCompat.setMetadata(
            MediaMetadataCompat.Builder().putString(MediaMetadata.METADATA_KEY_TITLE, song.name)
                .putString(MediaMetadata.METADATA_KEY_ARTIST, song.singer).build()
        )

        mBuilder.addAction(
            android.R.drawable.ic_media_previous, "rw30", pendingIntentMusic(
                ACTION_PREVIOUS, PREVIOUS_INTENT_REQUEST_CODE
            )
        )
            .addAction(
                android.R.drawable.ic_media_pause, "Pause", pendingIntentMusic(
                    ACTION_PAUSE,
                    PAUSE_INTENT_REQUEST_CODE
                )
            )
            .addAction(
                android.R.drawable.ic_media_next,
                "ff30",
                pendingIntentMusic(ACTION_NEXT, NEXT_INTENT_REQUEST_CODE)
            )
            .addAction(
                android.R.drawable.ic_menu_close_clear_cancel, "Stop", pendingIntentMusic(
                    ACTION_STOP, STOP_INTENT_REQUEST_CODE
                )
            ).setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
                    .setMediaSession(mMediaSessionCompat.sessionToken)
            ).setContentTitle(song.name).setContentText(song.singer)
            .setSmallIcon(R.drawable.ic_music).setPriority(Notification.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntentResume())
        try {
            mBuilder.setLargeIcon(getBitmapSong(song.path))
        } catch (_: java.lang.Exception) {
            mBuilder.setLargeIcon(
                BitmapFactory.decodeResource(
                    Resources.getSystem(), R.drawable.ic_music
                )
            )
        }
        startForeground(FOREGROUND_SERVICE_ID, mBuilder.build())
    }

    private fun pendingIntentMusic(action: String, requestCode: Int): PendingIntent? {
        val intent = Intent()
        intent.action = action
        return PendingIntent.getBroadcast(
            this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }


    private fun pendingIntentResume(): PendingIntent? {
        val resumeIntent = Intent(this, MainActivity::class.java)
        return PendingIntent.getBroadcast(
            this, RESUME_INTENT_REQUEST_CODE, resumeIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }


    override fun onDestroy() {
        stopForeground(true)
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}