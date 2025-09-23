package com.test.slideshow.ui.view

import com.test.slideshow.data.playlist.model.MediaType
import com.test.slideshow.data.playlist.model.PlaylistItem

fun PlaylistItem.toSlide(): Slide {
    return when (this.mediaType) {
        MediaType.Image -> Slide.Image(
            url = this.mediaResourceUrl,
            duration = this.duration
        )

        MediaType.Video -> Slide.Video(
            url = this.mediaResourceUrl,
            duration = this.duration
        )
    }
}