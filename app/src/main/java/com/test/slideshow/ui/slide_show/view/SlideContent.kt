package com.test.slideshow.ui.slide_show.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.test.slideshow.data.playlist.model.MediaType
import com.test.slideshow.data.playlist.model.PlaylistItem

@Composable
fun SlideContent(
    item: PlaylistItem,
    alpha: Float,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Box(modifier = modifier.graphicsLayer { this.alpha = alpha }) {
        when (item.mediaType) {
            MediaType.Image -> {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(item.mediaResourceLink)
                        .crossfade(false)
                        .build(),
                    contentDescription = "Slideshow Image",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

            MediaType.Video -> VideoPlayer(
                url = item.mediaResourceLink,
                modifier = Modifier.fillMaxSize(),
                autoPlay = true
            )
        }
    }
}