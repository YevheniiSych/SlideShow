package com.test.slideshow.data.playlist.mapper

import com.test.slideshow.data.playlist.db.PlaylistItemEntity
import com.test.slideshow.data.playlist.model.PlaylistsResponse

fun PlaylistsResponse.PlaylistDto.toPlayListItemsEntity(): List<PlaylistItemEntity> {
    return this.playlistItems.map {
        PlaylistItemEntity(
            creativeRefKey = it.creativeRefKey,
            duration = it.duration,
            expireDate = it.expireDate,
            startDate = it.startDate,
            collectStatistics = it.collectStatistics,
            creativeLabel = it.creativeLabel,
            slidePriority = it.slidePriority,
            creativeKey = it.creativeKey,
            playlistKey = it.playlistKey,
            orderKey = it.orderKey,
            eventTypesList = it.eventTypesList
        )
    }
}