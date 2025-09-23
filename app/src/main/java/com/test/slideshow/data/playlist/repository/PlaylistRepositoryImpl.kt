package com.test.slideshow.data.playlist.repository

import com.test.slideshow.BuildConfig
import com.test.slideshow.data.common.FileDownloader
import com.test.slideshow.data.playlist.datasource.PlayListRemoteDataSource
import com.test.slideshow.data.playlist.datasource.PlaylistLocalDataSource
import com.test.slideshow.data.playlist.mapper.toPlaylistItem
import com.test.slideshow.data.playlist.mapper.toPlaylistItemEntity
import com.test.slideshow.data.playlist.model.PlaylistItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val remoteDataSource: PlayListRemoteDataSource,
    private val localDataSource: PlaylistLocalDataSource,
    private val fileDownloader: FileDownloader
) : PlaylistRepository {


    override fun getPlaylistItems(playlistKey: String): Flow<List<PlaylistItem>> {
        return localDataSource.getPlaylistItems(playlistKey)
            .map { list -> list.map { it.toPlaylistItem() } }
    }

    override suspend fun refreshPlaylist(playlistKey: String) {
        remoteDataSource.fetchPlaylists(playlistKey)
            .onSuccess { playlistsResponse ->
                val playlistsEntity = playlistsResponse.playlists
                    .flatMap {
                        it.playlistItems.map { playlistDto ->
                            playlistDto.toPlaylistItemEntity()
                        }
                    }
                // Save just received playlist items to DB
                localDataSource.savePlaylist(playlistsEntity)

                // Download files and update localUri in DB
                playlistsEntity.onEach {
                    val url = "${BuildConfig.BASE_URL}/PlayerBackend/creative/get/${it.creativeKey}"
                    val file = fileDownloader.downloadFile(url, it.creativeKey)
                    val updated = it.copy(localUri = file.toURI().toString())
                    localDataSource.updatePlaylistItem(updated)
                }
            }
            .onFailure {

            }
    }


}