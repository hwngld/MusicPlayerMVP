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
import com.sildev.musicplayer.model.Song


class SongAdapter(private val iClickSongItem: IClickSongItem) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    interface IClickSongItem {
        fun onClickItem(position: Int)
    }

    private var _listSong: List<Song> = ArrayList()
    private var resultSongList: MutableList<Song> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song: Song = resultSongList[position]
        holder.tvSongTitle.text = song.name
        holder.tvSinger.text = song.singer
        holder.itemView.setOnClickListener {
            iClickSongItem.onClickItem(_listSong.indexOf(song))
        }
        try {
            holder.imgSong.setImageBitmap(getBitmapSong(song.path))
        } catch (e: java.lang.Exception) {
            holder.imgSong.setImageResource(R.drawable.ic_music)
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

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgSong: ImageView
        var tvSongTitle: TextView
        var tvSinger: TextView

        init {
            imgSong = itemView.findViewById(R.id.image_song)
            tvSongTitle = itemView.findViewById(R.id.text_title)
            tvSinger = itemView.findViewById(R.id.text_singer)

        }
    }
}