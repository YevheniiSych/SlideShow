package com.test.slideshow.ui.slide_show.view

import android.view.TextureView
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    url: String,
    modifier: Modifier = Modifier,
    autoPlay: Boolean = true,
) {

    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = ExoPlayer.REPEAT_MODE_ALL
        }
    }

    DisposableEffect(url) {
        val mediaItem = MediaItem.fromUri(url)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = autoPlay

        onDispose {
            exoPlayer.release()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize(),
            factory = { context ->
                TextureView(context).apply {
                    exoPlayer.setVideoTextureView(this)

                    exoPlayer.addListener(object : Player.Listener {
                        override fun onVideoSizeChanged(videoSize: VideoSize) {
                            adjustTextureView(this@apply, videoSize)
                        }
                    })
                }
            }
        )
    }
}

fun adjustTextureView(textureView: TextureView, videoSize: VideoSize) {
    val viewW = textureView.width.toFloat()
    val viewH = textureView.height.toFloat()
    val videoW = videoSize.width.toFloat()
    val videoH = videoSize.height.toFloat()

    if (viewW <= 0 || viewH <= 0 || videoW <= 0 || videoH <= 0) return

    val viewAspect = viewW / viewH
    val videoAspect = videoW / videoH

    val scaleX: Float
    val scaleY: Float

    if (videoAspect > viewAspect) {
        scaleX = viewW / videoW
        scaleY = scaleX
    } else {
        scaleY = viewH / videoH
        scaleX = scaleY
    }

    val matrix = android.graphics.Matrix()
    matrix.setScale(scaleX * videoW / viewW, scaleY * videoH / viewH, viewW / 2, viewH / 2)
    textureView.setTransform(matrix)
}