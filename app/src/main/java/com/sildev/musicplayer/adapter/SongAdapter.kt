package com.sildev.musicplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sildev.musicplayer.MusicPlayerHelper.getBitmapSong
import com.sildev.musicplayer.MusicPlayerHelper.removeAccent
import com.sildev.musicplayer.R
import com.sildev.musicplayer.databinding.ItemSongBinding
import com.sildev.musicplayer.model.Song


class SongAdapter(private val onClickItem: (Int) -> Unit) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    private var _listSong = listOf<Song>()
    private var resultSongList = mutableListOf<Song>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song: Song = resultSongList[position]
        holder.apply {
            itemBinding.textTitle.text = song.name
            itemBinding.textSinger.text = song.singer
            itemBinding.root.setOnClickListener {
                onClickItem(position)
            }
            Glide.with(itemView.context).load(getBitmapSong(song.path))
                .placeholder(R.drawable.ic_music).into(itemBinding.imageSong)
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
            if (removeAccent(song.name).contains(
                    removeAccent(key),
                    true
                ) || removeAccent(song.singer).contains(removeAccent(key), true)
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
