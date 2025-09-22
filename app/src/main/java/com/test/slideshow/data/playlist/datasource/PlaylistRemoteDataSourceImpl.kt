package com.test.slideshow.data.playlist.datasource

import com.test.slideshow.data.playlist.api.PlaylistApi
import com.test.slideshow.data.playlist.api.PlaylistsResponse
import javax.inject.Inject

class PlaylistRemoteDataSourceImpl @Inject constructor(
    private val playlistApi: PlaylistApi
) : PlayListRemoteDataSource {

    override suspend fun fetchPlaylists(playlistKey: String): Result<PlaylistsResponse> {
        return try {
            val response = playlistApi.fetchPlaylists(playlistKey)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}