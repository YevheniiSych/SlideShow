package com.test.slideshow.di

import com.test.slideshow.data.common.FileDownloader
import com.test.slideshow.data.common.db.AppDB
import com.test.slideshow.data.playlist.api.PlaylistApi
import com.test.slideshow.data.playlist.datasource.PlayListRemoteDataSource
import com.test.slideshow.data.playlist.datasource.PlaylistLocalDataSource
import com.test.slideshow.data.playlist.datasource.PlaylistLocalDataSourceImpl
import com.test.slideshow.data.playlist.datasource.PlaylistRemoteDataSourceImpl
import com.test.slideshow.data.playlist.db.PlaylistDao
import com.test.slideshow.data.playlist.repository.PlaylistRepository
import com.test.slideshow.data.playlist.repository.PlaylistRepositoryImpl
import com.test.slideshow.domain.playlist.PlaylistUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PlaylistModule {

    @Provides
    @Singleton
    fun providePlaylistDao(db: AppDB): PlaylistDao {
        return db.playlistDao()
    }

    @Provides
    @Singleton
    fun providePlaylistApi(retrofit: Retrofit): PlaylistApi {
        return retrofit
            .create(PlaylistApi::class.java)
    }

    @Provides
    @Singleton
    fun providePlaylistsLocalDataSource(dao: PlaylistDao): PlaylistLocalDataSource {
        return PlaylistLocalDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun providePlaylistsRemoteDataSource(api: PlaylistApi): PlayListRemoteDataSource {
        return PlaylistRemoteDataSourceImpl(api)
    }

    @Provides
    @Singleton
    fun providePlaylistRepository(
        remoteDataSource: PlayListRemoteDataSource,
        localDataSource: PlaylistLocalDataSource,
        fileDownloader: FileDownloader
    ): PlaylistRepository {
        return PlaylistRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            fileDownloader = fileDownloader
        )
    }

    @Provides
    @Singleton
    fun providePlaylistUseCase(repository: PlaylistRepository): PlaylistUseCase {
        return PlaylistUseCase(repository)
    }
}