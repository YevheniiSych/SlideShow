package com.test.slideshow.di

import android.content.Context
import androidx.room.Room
import com.test.slideshow.BuildConfig
import com.test.slideshow.data.common.FileDownloader
import com.test.slideshow.data.common.db.AppDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
    }

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
    fun provideFileDownloader(@ApplicationContext context: Context): FileDownloader {
        return FileDownloader(context)
    }
}