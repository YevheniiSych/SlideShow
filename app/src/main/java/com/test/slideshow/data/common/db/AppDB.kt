package com.test.slideshow.data.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.slideshow.data.playlist.db.PlaylistConverters
import com.test.slideshow.data.playlist.db.PlaylistDao
import com.test.slideshow.data.playlist.db.PlaylistItemEntity

@Database(
    entities = [PlaylistItemEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PlaylistConverters::class)
abstract class AppDB : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
}