package com.test.slideshow.ui.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

@Composable
fun SlideContent(
    slide: Slide,
    alpha: Float,
    modifier: Modifier = Modifier
) {
    when (slide) {
        is Slide.Image -> GlideImageSlide(slide.url, alpha, modifier)
        is Slide.Video -> VideoSlide(slide.url, alpha, modifier)
    }
}

@Composable
fun GlideImageSlide(url: String, alpha: Float, modifier: Modifier) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(url) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(res: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap = res
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = null,
            modifier = modifier.graphicsLayer { this.alpha = alpha },
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun VideoSlide(url: String, alpha: Float, modifier: Modifier) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            playWhenReady = true
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
            }
        },
        modifier = modifier.graphicsLayer { this.alpha = alpha }
    )
}