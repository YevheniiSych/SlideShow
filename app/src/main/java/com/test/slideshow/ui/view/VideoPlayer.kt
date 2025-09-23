package com.test.slideshow.ui.view

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.launch

@Composable
fun FadingVideoPlayer(
    url: String,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = true,
    crossfadeMs: Int = 2000
) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
        }
    }

    val alpha = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    DisposableEffect(url) {
        val mediaItem = MediaItem.fromUri(url)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        if (autoPlay) exoPlayer.playWhenReady = true

        scope.launch {
            alpha.snapTo(0f)
            alpha.animateTo(1f, animationSpec = tween(crossfadeMs))
        }

        onDispose {
            exoPlayer.stop()
            exoPlayer.release()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { this.alpha = alpha.value },
            factory = {
                PlayerView(context).apply {
                    useController = false
                    layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                    player = exoPlayer
                }
            }
        )
    }
}