package com.sildev.musicplayer

import android.app.Dialog
import android.content.*
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sildev.musicplayer.MusicPlayerHelper.parseLongToTime
import com.sildev.musicplayer.databinding.BottomsheetControlMusicBinding
import com.sildev.musicplayer.model.Song
import com.sildev.musicplayer.presenter.MainContract

class ControlMusicBottomSheetDialog(
    private val mainPresenter: MainContract.Presenter,
) : BottomSheetDialogFragment(), SeekBar.OnSeekBarChangeListener {

    private val bottomSheetDialogBinding: BottomsheetControlMusicBinding by lazy {
        BottomsheetControlMusicBinding.inflate(
            layoutInflater, null, false
        )
    }
    private val handlerUpdateTime = Handler()
    lateinit var mediaPlayer: MediaPlayer
    private val musicReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                ACTION_PLAY -> {
                    updateCurrentTime(0)
                }
                ACTION_PAUSE -> {
                    setIconPauseResume()
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_PAUSE)
        intentFilter.addAction(ACTION_PLAY)
        context?.registerReceiver(musicReceiver, intentFilter)
    }

    override fun onDestroy() {
        context?.unregisterReceiver(musicReceiver)
        super.onDestroy()

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog: BottomSheetDialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val behavior = bottomSheetDialog.behavior
        behavior.isDraggable = false
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.setContentView(bottomSheetDialogBinding.root)
        setDataBottomSheet()
        handlerUpdateTime.postDelayed(object : Runnable {
            override fun run() {
                updateCurrentTime(mediaPlayer.currentPosition)
                handlerUpdateTime.postDelayed(this, DELAY_UPDATE_TIME)
            }

        }, 1)
        return bottomSheetDialog
    }


    private fun setDataBottomSheet() {
        setIconShuffle()
        setIconRepeat()
        setOnClickView()
    }


    private fun setIconShuffle() {
        if (mainPresenter.isShuffle()) {
            bottomSheetDialogBinding.imageShuffle.setImageResource(R.drawable.ic_shuffle)
        } else {
            bottomSheetDialogBinding.imageShuffle.setImageResource(R.drawable.ic_not_shuffle)
        }
    }

    //
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

    fun setIconPauseResume() {
        val icon = if (mediaPlayer.isPlaying) {
            R.drawable.ic_pause
        } else {
            R.drawable.ic_play
        }
        bottomSheetDialogBinding.imagePauseResume.setImageResource(icon)
    }

    private fun sendMusicBroadcast(action: String) {
        val intent = Intent()
        intent.action = action
        context?.sendBroadcast(intent)
    }

    fun updateSong(song: Song) {
        bottomSheetDialogBinding.apply {
            seekbarTime.max = song.duration
            textTitle.text = song.name
            textSinger.text = song.singer
            textTotalTime.text = parseLongToTime(song.duration)
            context?.let {
                Glide.with(it).load(MusicPlayerHelper.getBitmapSong(song.path))
                    .placeholder(R.drawable.ic_music).into(imageSong)
            }
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
