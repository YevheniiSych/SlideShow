package com.test.slideshow.di

import android.content.Context
import androidx.room.Room
import com.test.slideshow.data.playlist.api.PlaylistApi
import com.test.slideshow.data.playlist.db.AppDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PlaylistModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDB {
        return Room.databaseBuilder(
            context = context,
            klass = AppDB::class.java,
            name = "app_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePlaylistDao(db: AppDB) = db.playlistDao()

    @Provides
    @Singleton
    fun providePlaylistApi(retrofit: Retrofit): PlaylistApi {
        return retrofit.create(PlaylistApi::class.java)
    }

}