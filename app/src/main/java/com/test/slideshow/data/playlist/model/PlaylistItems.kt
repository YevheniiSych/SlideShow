package com.test.slideshow.data.playlist.model


import com.google.gson.annotations.SerializedName

data class PlaylistItems(
    @SerializedName("collectStatistics")
    val collectStatistics: Boolean,
    @SerializedName("creativeKey")
    val creativeKey: String,
    @SerializedName("creativeLabel")
    val creativeLabel: String,
    @SerializedName("creativeRefKey")
    val creativeRefKey: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("eventTypesList")
    val eventTypesList: List<Any>,
    @SerializedName("expireDate")
    val expireDate: String,
    @SerializedName("orderKey")
    val orderKey: Int,
    @SerializedName("playlistKey")
    val playlistKey: String,
    @SerializedName("slidePriority")
    val slidePriority: Int,
    @SerializedName("startDate")
    val startDate: String
)