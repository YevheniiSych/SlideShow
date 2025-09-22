package com.test.slideshow.data.playlist.datasource


import com.test.slideshow.data.playlist.model.PlaylistsResponse

interface PlayListRemoteDataSource {
    suspend fun fetchPlaylists(playlistKey: String): Result<List<PlaylistsResponse>>
}