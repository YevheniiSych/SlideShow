package com.test.slideshow.ui.slide_show.model

import com.test.slideshow.data.playlist.model.PlaylistItem
import com.test.slideshow.ui.view.Slide
import com.test.slideshow.ui.view.toSlide

data class SlideShowUiState(
    val playlistItems: List<PlaylistItem> = listOf(),
    val slideIndex: Int = 0
) {
    val slides: List<Slide> get() = playlistItems.map { it.toSlide() }
}