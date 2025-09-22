package com.test.slideshow.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: PlaylistItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<PlaylistItemEntity>)

    @Update
    suspend fun updateItem(item: PlaylistItemEntity)

    @Delete
    suspend fun deleteItem(item: PlaylistItemEntity)

    @Query("DELETE FROM playlist_items")
    suspend fun clearAll()

    @Query("SELECT * FROM playlist_items")
    fun getAllItems(): Flow<List<PlaylistItemEntity>>

    @Query("SELECT * FROM playlist_items WHERE playlistKey = :playlistKey ORDER BY orderKey ASC")
    fun getItemsByPlaylist(playlistKey: String): Flow<List<PlaylistItemEntity>>
}