package com.test.slideshow.data.playlist.model


import com.google.gson.annotations.SerializedName

data class Playlists(
    @SerializedName("channelTime")
    val channelTime: Int,
    @SerializedName("playlistItems")
    val playlistItems: List<PlaylistItems>,
    @SerializedName("playlistKey")
    val playlistKey: String
)