package com.test.slideshow.domain.playlist

import com.test.slideshow.data.playlist.model.PlaylistItem
import com.test.slideshow.data.playlist.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistUseCase @Inject constructor (
    private val repository: PlaylistRepository
) {
    fun getSortedPlaylistItems(playlistKey: String): Flow<List<PlaylistItem>> {
        return repository.getPlaylistItems(playlistKey).map { itemList ->
            itemList.sortedBy { it.orderKey }
        }
    }

    suspend fun refreshPlaylist(playlistKey: String) {
        repository.refreshPlaylist(playlistKey)
    }
}