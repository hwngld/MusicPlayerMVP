package com.sildev.musicplayer

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sildev.musicplayer.MusicPlayerHelper.parseLongToTime
import com.sildev.musicplayer.databinding.BottomsheetControlMusicBinding
import com.sildev.musicplayer.model.Song
import com.sildev.musicplayer.presenter.MainContract

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
        bottomSheetDialogBinding.apply {
            imageRepeat.setOnClickListener {
                mainPresenter.setRepeat()
                setIconRepeat()
            }
            imageShuffle.setOnClickListener {
                mainPresenter.setShuffle()
                setIconShuffle()
            }
            imagePrevious.setOnClickListener {
                sendMusicBroadcast(ACTION_PREVIOUS)
            }
            imageNext.setOnClickListener {
                sendMusicBroadcast(ACTION_NEXT)
            }
            imagePauseResume.setOnClickListener {
                sendMusicBroadcast(ACTION_PAUSE)
            }
            imageBack.setOnClickListener {
                dismiss()
            }
        }
        bottomSheetDialogBinding.seekbarTime.setOnSeekBarChangeListener(this)

    }

    fun setIconPauseResume(icon: Int) {
        bottomSheetDialogBinding.imagePauseResume.setImageResource(icon)

    }

    private fun sendMusicBroadcast(action: String) {
        val intent = Intent()
        intent.action = action
        context.sendBroadcast(intent)
    }


    fun updateSong(song: Song) {
        bottomSheetDialogBinding.apply {
            seekbarTime.max = song.duration
            textTitle.text = song.name
            textSinger.text = song.singer
            textTotalTime.text = parseLongToTime(song.duration)
            Glide.with(context).load(MusicPlayerHelper.getBitmapSong(song.path))
                .placeholder(R.drawable.ic_music).into(imageSong)
        }

    }


    fun updateCurrentTime(progress: Int) {
        bottomSheetDialogBinding.apply {
            seekbarTime.progress = progress
            textCurrentTime.text = parseLongToTime(progress)
        }

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
