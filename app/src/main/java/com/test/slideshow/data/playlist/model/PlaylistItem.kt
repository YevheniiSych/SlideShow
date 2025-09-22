package com.test.slideshow.data.playlist.model

data class PlaylistItem(
    val creativeRefKey: String?,
    val duration: Int,
    val expireDate: String,
    val startDate: String,
    val creativeLabel: String,
    val slidePriority: Int,
    val creativeKey: String,
    val playlistKey: String,
    val orderKey: Int,
)
