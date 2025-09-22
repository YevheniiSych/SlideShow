package com.test.slideshow.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_items")
data class PlaylistItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "creativeRefKey")
    val creativeRefKey: String?,

    @ColumnInfo(name = "duration")
    val duration: Int,

    @ColumnInfo(name = "expireDate")
    val expireDate: String,

    @ColumnInfo(name = "startDate")
    val startDate: String,

    @ColumnInfo(name = "collectStatistics")
    val collectStatistics: Boolean,

    @ColumnInfo(name = "creativeLabel")
    val creativeLabel: String,

    @ColumnInfo(name = "slidePriority")
    val slidePriority: Int,

    @ColumnInfo(name = "creativeKey")
    val creativeKey: String,

    @ColumnInfo(name = "playlistKey")
    val playlistKey: String,

    @ColumnInfo(name = "orderKey")
    val orderKey: Int,

    @ColumnInfo(name = "eventTypesList")
    val eventTypesList: List<String>
)