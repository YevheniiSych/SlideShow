package com.test.slideshow.data.playlist.datasource

import com.test.slideshow.data.playlist.db.PlaylistDao
import com.test.slideshow.data.playlist.db.PlaylistItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistLocalDataSourceImpl @Inject constructor(
    private val playlistDao: PlaylistDao
) : PlaylistLocalDataSource {

    override fun getPlaylistItems(playlistKey: String): Flow<List<PlaylistItemEntity>> {
        return playlistDao.getAllItems()
    }

    override suspend fun savePlaylist(items: List<PlaylistItemEntity>) {
        try {
            playlistDao.insertItems(items)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}