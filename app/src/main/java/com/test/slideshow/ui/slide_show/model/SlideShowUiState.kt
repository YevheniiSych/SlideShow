package com.test.slideshow.ui.slide_show.model

import com.test.slideshow.data.playlist.model.PlaylistItem

data class SlideShowUiState(
    val playlistItems: List<PlaylistItem> = listOf(),
    val slideIndex: Int = 0,
    val slideCountdown: Int = 0
)