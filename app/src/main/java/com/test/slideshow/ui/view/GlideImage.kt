package com.test.slideshow.ui.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.launch

@Composable
fun GlideCrossfadeImage(
    url: String,
    modifier: Modifier = Modifier,
    durationMs: Int = 800
) {
    val context = LocalContext.current
    var currentBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var previousBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val currentAlpha = remember { Animatable(0f) }
    val previousAlpha = remember { Animatable(1f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(url) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // Changing current bitmap to previous
                    previousBitmap = currentBitmap
                    currentBitmap = resource

                    scope.launch {
                        // Clearing alphas
                        currentAlpha.snapTo(0f)
                        previousAlpha.snapTo(1f)

                        // Same time animations
                        val fadeOut = launch { previousAlpha.animateTo(0f, tween(durationMs)) }
                        val fadeIn = launch { currentAlpha.animateTo(1f, tween(durationMs)) }

                        fadeOut.join()
                        fadeIn.join()

                        // Clearing previous bitmap
                        previousBitmap = null
                    }
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    Box(modifier = modifier.fillMaxSize()) {
        previousBitmap?.let { bmp ->
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
                alpha = previousAlpha.value
            )
        }
        currentBitmap?.let { bmp ->
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
                alpha = currentAlpha.value
            )
        }
    }
}