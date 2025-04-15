package com.ys.player.common

data class PlayerState(
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val volume: Float = 1.0f,
    val isMuted: Boolean = false,
    val isBuffering: Boolean = false,
    val error: String? = null
)

sealed class PlayerEvent {
    data object Play : PlayerEvent()
    data object Pause : PlayerEvent()
    data object Stop : PlayerEvent()
    data class SeekTo(val position: Long) : PlayerEvent()
    data class SetVolume(val volume: Float) : PlayerEvent()
    data object ToggleMute : PlayerEvent()
    data class SetLoopDuration(val duration: Long?) : PlayerEvent()
} 