package com.sildev.musicplayer

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sildev.musicplayer.MusicPlayerHelper.parseLongToTime
import com.sildev.musicplayer.model.Song
import com.sildev.musicplayer.presenter.MainPresenter

class ControlMusicBottomSheetDialog(
    view: View,
    context: Context,
    private val mainPresenter: MainPresenter,
) : BottomSheetDialog(context), SeekBar.OnSeekBarChangeListener {
    private lateinit var tvTitle: TextView
    private lateinit var tvSinger: TextView
    private lateinit var tvTotalTime: TextView
    private lateinit var tvCurrentTime: TextView
    private lateinit var imgSong: ImageView
    private lateinit var imgNext: ImageView
    private lateinit var imgPauseResume: ImageView
    private lateinit var imgPrevious: ImageView
    private lateinit var imgShuffle: ImageView
    private lateinit var imgRepeat: ImageView
    private lateinit var sbTime: SeekBar
    private lateinit var imgBack: ImageView
    lateinit var mediaPlayer: MediaPlayer

    init {
        setContentView(view)
        val behavior = this.behavior
        behavior.isDraggable = false
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        initUI(view)

        setIconRepeat()
        setIconShuffle()
        setOnClickView()
    }

    private fun setIconShuffle() {
        if (mainPresenter.isShuffle()) {
            imgShuffle.setImageResource(R.drawable.ic_shuffle)
        } else {
            imgShuffle.setImageResource(R.drawable.ic_not_shuffle)
        }
    }

    private fun setIconRepeat() {
        if (mainPresenter.isRepeat()) {
            imgRepeat.setImageResource(R.drawable.ic_repeat_once)
        } else {
            imgRepeat.setImageResource(R.drawable.ic_repeat)
        }
    }

    private fun setOnClickView() {
        imgRepeat.setOnClickListener {
            mainPresenter.setRepeat()
            setIconRepeat()
        }
        imgShuffle.setOnClickListener {
            mainPresenter.setShuffle()
            setIconShuffle()
        }
        imgPrevious.setOnClickListener {
            sendMusicBroadcast(ACTION_PREVIOUS)
        }
        imgNext.setOnClickListener {
            sendMusicBroadcast(ACTION_NEXT)
        }
        imgPauseResume.setOnClickListener {
            sendMusicBroadcast(ACTION_PAUSE)
        }
        imgBack.setOnClickListener {
            dismiss()
        }
        sbTime.setOnSeekBarChangeListener(this)

    }

    fun setIconPauseResume(icon: Int) {
        imgPauseResume.setImageResource(icon)

    }

    private fun sendMusicBroadcast(action: String) {
        val intent: Intent = Intent()
        intent.action = action
        context.sendBroadcast(intent)
    }


    private fun initUI(view: View) {
        tvTitle = view.findViewById(R.id.text_title)
        tvSinger = view.findViewById(R.id.text_singer)
        tvTotalTime = view.findViewById(R.id.text_total_time)
        tvCurrentTime = view.findViewById(R.id.text_current_time)
        imgSong = view.findViewById(R.id.image_song)
        sbTime = view.findViewById(R.id.seekbar_time)
        imgNext = view.findViewById(R.id.image_next)
        imgPrevious = view.findViewById(R.id.image_previous)
        imgPauseResume = view.findViewById(R.id.image_pause_resume)
        imgShuffle = view.findViewById(R.id.image_shuffle)
        imgRepeat = view.findViewById(R.id.image_repeat)
        imgBack = view.findViewById(R.id.image_back)

    }

    fun updateSong(song: Song) {
        sbTime.max = song.duration
        tvTitle.text = song.name
        tvSinger.text = song.singer
        tvTotalTime.text = parseLongToTime(song.duration)
        try {
            imgSong.setImageBitmap(MusicPlayerHelper.getBitmapSong(song.path))
        } catch (_: java.lang.Exception) {
            imgSong.setImageResource(R.drawable.ic_music)
        }
    }


    fun updateCurrentTime(progress: Int) {
        sbTime.progress = progress
        tvCurrentTime.text = parseLongToTime(progress)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        tvCurrentTime.text = parseLongToTime(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        if (seekBar != null) {
            mediaPlayer.seekTo(seekBar.progress)
        }
    }
}