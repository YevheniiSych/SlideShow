package com.test.slideshow.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.test.slideshow.data.playlist.model.MediaType
import com.test.slideshow.data.playlist.model.PlaylistItem

@Composable
fun SlideContent(
    item: PlaylistItem,
    alpha: Float,
    duration: Int,
    modifier: Modifier = Modifier) {
    Box(modifier = modifier.graphicsLayer { this.alpha = alpha }) {
        when (item.mediaType) {
            MediaType.Image -> GlideCrossfadeImage(
                url = item.mediaResourceLink,
                modifier = Modifier.fillMaxSize(),
                durationMs = duration
            )
            MediaType.Video -> FadingVideoPlayer(
                url = item.mediaResourceLink,
                modifier = Modifier.fillMaxSize(),
                autoPlay = true,
                crossfadeMs = duration
            )
        }
    }
}