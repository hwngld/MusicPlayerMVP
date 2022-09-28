package com.sildev.musicplayer.model


import java.io.Serializable

class Song(val id: Long) : Serializable {
    var name: String = ""
    var singer: String = ""
    var path: String = ""
    var duration: Int = 0
    var albumId: Long = 0
    var album: String = ""
}