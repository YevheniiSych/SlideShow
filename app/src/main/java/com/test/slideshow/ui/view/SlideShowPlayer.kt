package com.test.slideshow.ui.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class Slide {
    data class Image(val url: String, val duration: Int) : Slide()
    data class Video(val url: String, val duration: Int) : Slide()
}

@Composable
fun SlideshowPlayer(
    slides: List<Slide>,
    modifier: Modifier = Modifier,
    crossfadeMs: Int = 1500
) {
    if (slides.isEmpty()) return

    var currentSlide by remember { mutableStateOf<Slide?>(null) }
    var previousSlide by remember { mutableStateOf<Slide?>(null) }

    val currentAlpha = remember { Animatable(1f) }
    val previousAlpha = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(slides) {
        var index = 0
        currentSlide = slides[0]

        while (true) {
            val slide = slides[index]

            val duration = when (slide) {
                is Slide.Image -> slide.duration
                is Slide.Video -> slide.duration
            }.toLong() * 1000L

            delay(duration)

            val nextIndex = (index + 1) % slides.size
            val nextSlide = slides[nextIndex]

            previousSlide = currentSlide
            currentSlide = nextSlide

            scope.launch {
                previousAlpha.snapTo(1f)
                currentAlpha.snapTo(0f)

                val fadeOut = launch { previousAlpha.animateTo(0f, tween(crossfadeMs)) }
                val fadeIn = launch { currentAlpha.animateTo(1f, tween(crossfadeMs)) }

                fadeOut.join()
                fadeIn.join()

                previousSlide = null
            }

            index = nextIndex
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        previousSlide?.let { slide ->
            SlideContent(
                slide = slide,
                alpha = previousAlpha.value,
                modifier = Modifier.fillMaxSize()
            )
        }

        currentSlide?.let { slide ->
            SlideContent(
                slide = slide,
                alpha = currentAlpha.value,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}