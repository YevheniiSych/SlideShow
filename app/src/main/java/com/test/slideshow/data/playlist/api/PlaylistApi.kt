package com.test.slideshow.data.playlist.api

import com.test.slideshow.data.playlist.model.PlaylistsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistApi {

    @GET("/PlayerBackend/screen/playlistItems/{playlistKey}")
    suspend fun fetchPlaylists(
        @Path("playlistKey")
        playlistKey: String,
    ): List<PlaylistsResponse>
}