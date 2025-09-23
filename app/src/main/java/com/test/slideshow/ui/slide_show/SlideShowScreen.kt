package com.test.slideshow.ui.slide_show

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.slideshow.ui.slide_show.model.SlideShowUiState
import com.test.slideshow.ui.view.SlideShowPlayer

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

            Spacer(Modifier.height(20.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Duration countdown: ${uiState.slideCountdown}s",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.Red
            )

            SlideShowPlayer(
                index = uiState.slideIndex,
                item = currentItem,
                crossfadeMs = 4000
            )
        }
    }
}