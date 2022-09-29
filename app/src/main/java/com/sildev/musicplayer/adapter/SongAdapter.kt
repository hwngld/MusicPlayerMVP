package com.sildev.musicplayer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sildev.musicplayer.MusicPlayerHelper.getBitmapSong
import com.sildev.musicplayer.MusicPlayerHelper.removeAccent
import com.sildev.musicplayer.R
import com.sildev.musicplayer.databinding.ItemSongBinding
import com.sildev.musicplayer.model.Song


class SongAdapter(private val iClickSongItem: IClickSongItem) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    interface IClickSongItem {
        fun onClickItem(position: Int)
    }

    private var _listSong: List<Song> = ArrayList()
    private var resultSongList: MutableList<Song> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song: Song = resultSongList[position]
        holder.itemBinding.textTitle.text = song.name
        holder.itemBinding.textSinger.text = song.singer
        holder.itemBinding.root.setOnClickListener {
            iClickSongItem.onClickItem(_listSong.indexOf(song))
        }
        try {
            holder.itemBinding.imageSong.setImageBitmap(getBitmapSong(song.path))
        } catch (e: java.lang.Exception) {
            holder.itemBinding.imageSong.setImageResource(R.drawable.ic_music)
        }
    }

    fun setDataToList(listSong: List<Song>) {
        _listSong = listSong
        resultSongList.addAll(0, listSong)
        notifyDataSetChanged()
    }

    fun searchSong(key: String) {
        resultSongList = ArrayList()
        for (song in _listSong) {
            if (removeAccent(song.name).contains(removeAccent(key), true)
                || removeAccent(song.singer).contains(removeAccent(key), true)
            ) {
                resultSongList.add(song)
            }

        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return resultSongList.size
    }

    class SongViewHolder(val itemBinding: ItemSongBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

    }
}