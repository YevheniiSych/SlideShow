package com.test.slideshow.ui.slide_show

import com.test.slideshow.data.playlist.model.PlaylistItem

data class SlideShowUiState(
    val playlistItems: List<PlaylistItem> = listOf()
)
