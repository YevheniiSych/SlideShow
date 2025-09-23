package com.test.slideshow.ui.slide_show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.slideshow.domain.playlist.PlaylistUseCase
import com.test.slideshow.ui.slide_show.model.SlideShowUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlideShowViewModel @Inject constructor(
    val playlistUseCase: PlaylistUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SlideShowUiState())
    val state: StateFlow<SlideShowUiState> = _state

    private var slideShowJob: Job? = null

    init {
        getPlaylists()
        refreshPlaylists()
    }

    private fun startSlideShow() {
        if (slideShowJob?.isActive == true) {
            return
        }

        slideShowJob = viewModelScope.launch {
            val mediaItems = state.value.playlistItems
            if (mediaItems.isEmpty()) {
                return@launch
            }
            var index = state.value.slideIndex
            while (isActive) {
                val item = mediaItems[index]

                _state.update { it.copy(slideIndex = index) }

                startCountdown(
                    duration = item.duration,
                    onTick = { countdown ->
                        _state.update { it.copy(slideCountdown = countdown) }
                    }
                )

                if (mediaItems.size - 1 == index) {
                    index = 0
                } else {
                    index++
                }
            }
        }
    }

    private fun stopSlideShow() {
        slideShowJob?.cancel()
        slideShowJob = null
    }

    private fun getPlaylists() {
        playlistUseCase.getSortedPlaylistItems("e490b14d-987d-414f-a822-1e7703b37ce4")
            .onEach { playlistItems ->
                _state.update {
                    it.copy(
                        playlistItems = playlistItems
                    )
                }
                if (playlistItems.isNotEmpty()) {
                    startSlideShow()
                }
            }
            .launchIn(viewModelScope)
    }

    private fun refreshPlaylists() {
        viewModelScope.launch {
            while (isActive) {
                playlistUseCase.refreshPlaylist("e490b14d-987d-414f-a822-1e7703b37ce4")
                delay(10000)
            }
        }
    }

    suspend fun startCountdown(
        duration: Int,
        intervalMs: Long = 1000,
        onTick: (Int) -> Unit,
    ) {
        var counter = duration
        while (counter != 0) {
            delay(intervalMs)
            counter--
            onTick(counter)
        }
    }
}