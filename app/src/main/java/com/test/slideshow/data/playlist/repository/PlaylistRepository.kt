package com.test.slideshow.data.playlist.repository

import com.test.slideshow.data.playlist.model.PlaylistItem
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun getPlaylistItems(playlistKey: String): Flow<List<PlaylistItem>>
    suspend fun refreshPlaylist(playlistKey: String)
}