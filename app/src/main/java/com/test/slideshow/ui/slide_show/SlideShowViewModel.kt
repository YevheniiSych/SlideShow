package com.test.slideshow.ui.slide_show

import androidx.lifecycle.ViewModel
import com.test.slideshow.domain.playlist.PlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SlideShowViewModel @Inject constructor(
    val playlistUseCase: PlaylistUseCase
): ViewModel() {
}