package com.test.slideshow.data.playlist.datasource

import com.test.slideshow.data.playlist.db.PlaylistItemEntity
import kotlinx.coroutines.flow.Flow

interface PlaylistLocalDataSource {
    fun getPlaylistItems(playlistKey: String): Flow<List<PlaylistItemEntity>>
    suspend fun savePlaylist(items: List<PlaylistItemEntity>)
    suspend fun updatePlaylistItem(item: PlaylistItemEntity)
}