package com.test.slideshow.data.playlist.model


import com.google.gson.annotations.SerializedName

data class PlaylistsResponse(
    @SerializedName("breakpointInterval")
    val breakpointInterval: Int,
    @SerializedName("company")
    val company: String,
    @SerializedName("modified")
    val modified: Long,
    @SerializedName("playlists")
    val playlists: List<Playlists>,
    @SerializedName("screenKey")
    val screenKey: String
)