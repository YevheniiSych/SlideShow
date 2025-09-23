package com.test.slideshow.ui.slide_show

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.test.slideshow.data.playlist.model.MediaType
import com.test.slideshow.ui.slide_show.model.SlideShowUiState
import com.test.slideshow.ui.view.FadingVideoPlayer

@Composable
fun SlideShowScreen(
    uiState: SlideShowUiState
) {
    val context = LocalContext.current

    val currentItem = uiState.playlistItems.getOrNull(uiState.slideIndex)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        if (currentItem != null) {
            when (currentItem.mediaType) {
                MediaType.Image -> {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(currentItem.mediaResourceUrl)
                            .crossfade(4000)
                            .build(),
                        contentDescription = "Slideshow Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f),
                        contentScale = ContentScale.Fit
                    )
                }

                MediaType.Video -> {
                    FadingVideoPlayer(
                        url = currentItem.mediaResourceUrl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f),
                        autoPlay = true,
                        crossfadeMs = 2000
                    )
                }
            }
        }
    }
}