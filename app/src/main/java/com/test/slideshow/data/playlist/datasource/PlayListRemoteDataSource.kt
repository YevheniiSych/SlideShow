package com.test.slideshow.data.playlist.datasource


import com.test.slideshow.data.playlist.api.PlaylistsResponse

interface PlayListRemoteDataSource {
    suspend fun fetchPlaylists(playlistKey: String): Result<PlaylistsResponse>
}