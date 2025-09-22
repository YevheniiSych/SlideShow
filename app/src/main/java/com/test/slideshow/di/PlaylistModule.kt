package com.test.slideshow.di

import com.test.slideshow.data.playlist.api.PlaylistApi
import com.test.slideshow.data.playlist.datasource.PlayListRemoteDataSource
import com.test.slideshow.data.playlist.datasource.PlaylistLocalDataSource
import com.test.slideshow.data.playlist.datasource.PlaylistLocalDataSourceImpl
import com.test.slideshow.data.playlist.datasource.PlaylistRemoteDataSourceImpl
import com.test.slideshow.data.playlist.db.AppDB
import com.test.slideshow.data.playlist.db.PlaylistDao
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
}