package com.sildev.musicplayer.model


import java.io.Serializable

class Song : Serializable {
    var id: Long = -1
    var name: String = ""
    var singer: String = ""
    var path: String = ""
    var duration: Int = 0
    var albumId: Long = 0
    var album: String = ""
}