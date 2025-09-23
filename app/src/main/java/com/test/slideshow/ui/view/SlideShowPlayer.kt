package com.test.slideshow.ui.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
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
import com.test.slideshow.data.playlist.model.PlaylistItem
import kotlinx.coroutines.launch

@Composable
fun SlideShowPlayer(
    index: Int,
    item: PlaylistItem,
    crossfadeMs: Int = 2000
) {
    val scope = rememberCoroutineScope()
    var previousItem by remember { mutableStateOf<PlaylistItem?>(null) }
    var currentItem by remember { mutableStateOf<PlaylistItem?>(null) }

    val currentAlpha = remember { Animatable(0f) }
    val previousAlpha = remember { Animatable(1f) }

    LaunchedEffect(index, item) {
        previousItem = currentItem
        currentItem = item

        previousAlpha.snapTo(1f)
        currentAlpha.snapTo(0f)

        scope.launch { previousAlpha.animateTo(0f, tween(crossfadeMs)) }
        scope.launch { currentAlpha.animateTo(1f, tween(crossfadeMs)) }
    }


    Box(modifier = Modifier.fillMaxSize()) {
        //previous slide
        previousItem?.let { item ->
            SlideContent(
                item = item,
                alpha = previousAlpha.value,
                duration = crossfadeMs,
            )
        }

        // current slide
        currentItem?.let { item ->
            SlideContent(
                item = item,
                alpha = currentAlpha.value,
                duration = crossfadeMs
            )
        }
    }
}