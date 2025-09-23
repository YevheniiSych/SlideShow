package com.test.slideshow

import com.test.slideshow.data.common.FileDownloader
import com.test.slideshow.data.playlist.api.PlaylistsResponse
import com.test.slideshow.data.playlist.datasource.PlayListRemoteDataSource
import com.test.slideshow.data.playlist.datasource.PlaylistLocalDataSource
import com.test.slideshow.data.playlist.db.PlaylistItemEntity
import com.test.slideshow.data.playlist.repository.PlaylistRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class PlaylistRepositoryTest {

    private lateinit var repository: PlaylistRepositoryImpl

    @Mock
    private lateinit var remoteDataSource: PlayListRemoteDataSource

    @Mock
    private lateinit var localDataSource: PlaylistLocalDataSource

    @Mock
    private lateinit var fileDownloader: FileDownloader

    @get:Rule
    val mainDispatcherRule = MainDispatchersRule() // custom rule to use TestDispatcher

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = PlaylistRepositoryImpl(remoteDataSource, localDataSource, fileDownloader)
    }

    @Test
    fun `refreshPlaylist should save and update local data`() = runTest {
        // given
        val playlistKey = "testKey"
        val playlistDto = PlaylistsResponse.PlaylistDto(
            channelTime = 0,
            playlistItems = listOf(
                PlaylistsResponse.PlaylistDto.PlaylistItemDto(
                    creativeKey = "123",
                    orderKey = 1,
                    creativeRefKey = "ref123",
                    duration = 10,
                    expireDate = "313",
                    startDate = "443",
                    collectStatistics = false,
                    creativeLabel = "label",
                    slidePriority = 1,
                    eventTypesList = emptyList(),
                    playlistKey = "testKey"
                )
            ),
            playlistKey = "testKey",
        )
        val response = PlaylistsResponse(
            playlists = listOf(
                playlistDto
            ),
            breakpointInterval = 1,
            company = "TestCompany",
            modified = 0L,
            screenKey = "screen123"
        )

        whenever(remoteDataSource.fetchPlaylists(playlistKey))
            .thenReturn(Result.success(response))

        val fakeFile = File("testFile.jpg")
        whenever(fileDownloader.downloadFile(any(), any())).thenReturn(fakeFile)

        // when
        repository.refreshPlaylist(playlistKey)

        // then
        verify(localDataSource).savePlaylist(
            check {
                assertEquals(1, it.size)
                assertEquals("123", it.first().creativeKey)
            }
        )

        verify(localDataSource).updatePlaylistItem(
            check {
                assertEquals(fakeFile.toURI().toString(), it.localUri)
            }
        )
    }

    @Test
    fun `getPlaylistItems should map entities to domain`() = runTest {
        val playlistKey = "testKey"
        val entity = PlaylistItemEntity(
            creativeKey = "123",
            localUri = null,
            orderKey = 1,
            creativeRefKey = "ref123",
            duration = 10,
            expireDate = "313",
            startDate = "443",
            collectStatistics = false,
            creativeLabel = "label",
            slidePriority = 1,
            playlistKey = "testKey",
            eventTypesList = emptyList()
        )
        whenever(localDataSource.getPlaylistItems(playlistKey))
            .thenReturn(flowOf(listOf(entity)))

        val result = repository.getPlaylistItems(playlistKey).first()

        assertEquals(1, result.size)
        assertEquals("123", result.first().creativeKey)
    }
}