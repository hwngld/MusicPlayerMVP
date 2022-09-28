package com.sildev.musicplayer

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sildev.musicplayer.MusicPlayerHelper.parseLongToTime
import com.sildev.musicplayer.databinding.BottomsheetControlMusicBinding
import com.sildev.musicplayer.model.Song
import com.sildev.musicplayer.presenter.MainContract
import com.sildev.musicplayer.presenter.MainPresenter

class ControlMusicBottomSheetDialog(
    context: Context,
    private val mainPresenter: MainContract.Presenter,
) : BottomSheetDialog(context), SeekBar.OnSeekBarChangeListener {
    private val bottomSheetDialogBinding: BottomsheetControlMusicBinding by lazy {
        BottomsheetControlMusicBinding.inflate(
            layoutInflater,
            null,
            false
        )
    }
    lateinit var mediaPlayer: MediaPlayer

    init {
        setContentView(bottomSheetDialogBinding.root)
        val behavior = this.behavior
        behavior.isDraggable = false
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        setIconRepeat()
        setIconShuffle()
        setOnClickView()
    }

    private fun setIconShuffle() {
        if (mainPresenter.isShuffle()) {
            bottomSheetDialogBinding.imageShuffle.setImageResource(R.drawable.ic_shuffle)
        } else {
            bottomSheetDialogBinding.imageShuffle.setImageResource(R.drawable.ic_not_shuffle)
        }
    }

    private fun setIconRepeat() {
        if (mainPresenter.isRepeat()) {
            bottomSheetDialogBinding.imageRepeat.setImageResource(R.drawable.ic_repeat_once)
        } else {
            bottomSheetDialogBinding.imageRepeat.setImageResource(R.drawable.ic_repeat)
        }
    }

    private fun setOnClickView() {
        bottomSheetDialogBinding.imageRepeat.setOnClickListener {
            mainPresenter.setRepeat()
            setIconRepeat()
        }
        bottomSheetDialogBinding.imageShuffle.setOnClickListener {
            mainPresenter.setShuffle()
            setIconShuffle()
        }
        bottomSheetDialogBinding.imagePrevious.setOnClickListener {
            sendMusicBroadcast(ACTION_PREVIOUS)
        }
        bottomSheetDialogBinding.imageNext.setOnClickListener {
            sendMusicBroadcast(ACTION_NEXT)
        }
        bottomSheetDialogBinding.imagePauseResume.setOnClickListener {
            sendMusicBroadcast(ACTION_PAUSE)
        }
        bottomSheetDialogBinding.imageBack.setOnClickListener {
            dismiss()
        }
        bottomSheetDialogBinding.seekbarTime.setOnSeekBarChangeListener(this)

    }

    fun setIconPauseResume(icon: Int) {
        bottomSheetDialogBinding.imagePauseResume.setImageResource(icon)

    }

    private fun sendMusicBroadcast(action: String) {
        val intent: Intent = Intent()
        intent.action = action
        context.sendBroadcast(intent)
    }


    fun updateSong(song: Song) {
        bottomSheetDialogBinding.seekbarTime.max = song.duration
        bottomSheetDialogBinding.textTitle.text = song.name
        bottomSheetDialogBinding.textSinger.text = song.singer
        bottomSheetDialogBinding.textTotalTime.text = parseLongToTime(song.duration)
        try {
            bottomSheetDialogBinding.imageSong.setImageBitmap(MusicPlayerHelper.getBitmapSong(song.path))
        } catch (e: java.lang.Exception) {
            bottomSheetDialogBinding.imageSong.setImageResource(R.drawable.ic_music)
        }
    }


    fun updateCurrentTime(progress: Int) {
        bottomSheetDialogBinding.seekbarTime.progress = progress
        bottomSheetDialogBinding.textCurrentTime.text = parseLongToTime(progress)
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        bottomSheetDialogBinding.textCurrentTime.text = parseLongToTime(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        if (seekBar != null) {
            mediaPlayer.seekTo(seekBar.progress)
        }
    }
}