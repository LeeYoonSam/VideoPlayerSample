package com.ys.player.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideoPlayerViewModel : ViewModel() {
    private val _playerState = MutableStateFlow(PlayerState())
    val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()

    fun onEvent(event: PlayerEvent) {
        viewModelScope.launch {
            when (event) {
                is PlayerEvent.Play -> {
                    _playerState.value = _playerState.value.copy(isPlaying = true)
                }
                is PlayerEvent.Pause -> {
                    _playerState.value = _playerState.value.copy(isPlaying = false)
                }
                is PlayerEvent.Stop -> {
                    _playerState.value = _playerState.value.copy(
                        isPlaying = false,
                        currentPosition = 0L
                    )
                }
                is PlayerEvent.SeekTo -> {
                    _playerState.value = _playerState.value.copy(currentPosition = event.position)
                }
                is PlayerEvent.SetVolume -> {
                    _playerState.value = _playerState.value.copy(
                        volume = event.volume,
                        isMuted = event.volume == 0f
                    )
                }
                is PlayerEvent.ToggleMute -> {
                    _playerState.value = _playerState.value.copy(
                        isMuted = !_playerState.value.isMuted
                    )
                }
                is PlayerEvent.SetLoopDuration -> {
                    // Loop duration will be handled by ExoPlayer directly
                }
            }
        }
    }

    fun updatePlayerState(
        isPlaying: Boolean? = null,
        currentPosition: Long? = null,
        duration: Long? = null,
        volume: Float? = null,
        isMuted: Boolean? = null,
        isBuffering: Boolean? = null,
        error: String? = null
    ) {
        _playerState.value = _playerState.value.copy(
            isPlaying = isPlaying ?: _playerState.value.isPlaying,
            currentPosition = currentPosition ?: _playerState.value.currentPosition,
            duration = duration ?: _playerState.value.duration,
            volume = volume ?: _playerState.value.volume,
            isMuted = isMuted ?: _playerState.value.isMuted,
            isBuffering = isBuffering ?: _playerState.value.isBuffering,
            error = error ?: _playerState.value.error
        )
    }
} 