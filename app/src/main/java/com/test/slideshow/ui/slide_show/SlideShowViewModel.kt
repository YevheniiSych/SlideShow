package com.test.slideshow.ui.slide_show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.slideshow.domain.playlist.PlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlideShowViewModel @Inject constructor(
    val playlistUseCase: PlaylistUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SlideShowUiState())
    val state: StateFlow<SlideShowUiState> = _state

    init {
        getPlaylists()
        refreshPlaylists()
    }

    private fun getPlaylists() {
        playlistUseCase.getSortedPlaylistItems("e490b14d-987d-414f-a822-1e7703b37ce4")
            .onEach {

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
}