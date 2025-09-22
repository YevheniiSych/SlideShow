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
    val playlists: List<PlaylistDto>,
    @SerializedName("screenKey")
    val screenKey: String
) {
    data class PlaylistDto(
        @SerializedName("channelTime")
        val channelTime: Int,
        @SerializedName("playlistItems")
        val playlistItems: List<PlaylistItemDto>,
        @SerializedName("playlistKey")
        val playlistKey: String
    ) {
        data class PlaylistItemDto(
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
            val eventTypesList: List<String>,
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
    }
}