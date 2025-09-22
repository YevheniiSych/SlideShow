package com.test.slideshow.data.playlist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PlaylistItemEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PlaylistConverters::class)
abstract class AppDB : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
}