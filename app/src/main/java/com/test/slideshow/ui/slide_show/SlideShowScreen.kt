package com.test.slideshow.ui.slide_show

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.test.slideshow.data.playlist.model.MediaType
import com.test.slideshow.ui.slide_show.model.SlideShowUiState
import com.test.slideshow.ui.view.FadingVideoPlayer
import com.test.slideshow.ui.view.GlideCrossfadeImage

@Composable
fun SlideShowScreen(
    uiState: SlideShowUiState
) {
    val currentItem = uiState.playlistItems.getOrNull(uiState.slideIndex)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        if (currentItem != null) {
            when (currentItem.mediaType) {
                MediaType.Image -> {
                    GlideCrossfadeImage(
                        url = currentItem.mediaResourceLink,
                        modifier = Modifier
                            .fillMaxSize(),
                        durationMs = 1500
                    )
                }

                MediaType.Video -> {
                    FadingVideoPlayer(
                        url = currentItem.mediaResourceLink,
                        modifier = Modifier
                            .fillMaxSize(),
                        autoPlay = true,
                        crossfadeMs = 2000
                    )
                }
            }
        }
    }
}