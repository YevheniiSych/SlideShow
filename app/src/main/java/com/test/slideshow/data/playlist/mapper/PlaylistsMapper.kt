package com.test.slideshow.data.playlist.mapper

import com.test.slideshow.data.playlist.api.PlaylistsResponse
import com.test.slideshow.data.playlist.db.PlaylistItemEntity
import com.test.slideshow.data.playlist.model.PlaylistItem

fun PlaylistsResponse.PlaylistDto.PlaylistItemDto.toPlaylistItemEntity(): PlaylistItemEntity {
    return PlaylistItemEntity(
        creativeRefKey = creativeRefKey,
        duration = duration,
        expireDate = expireDate,
        startDate = startDate,
        collectStatistics = collectStatistics,
        creativeLabel = creativeLabel,
        slidePriority = slidePriority,
        creativeKey = creativeKey,
        playlistKey = playlistKey,
        orderKey = orderKey,
        eventTypesList = eventTypesList
    )
}


fun PlaylistItemEntity.toPlaylistItem(): PlaylistItem {
    return PlaylistItem(
        creativeRefKey = creativeRefKey,
        duration = duration,
        expireDate = expireDate,
        startDate = startDate,
        creativeLabel = creativeLabel,
        slidePriority = slidePriority,
        creativeKey = creativeKey,
        playlistKey = playlistKey,
        orderKey = orderKey
    )
}