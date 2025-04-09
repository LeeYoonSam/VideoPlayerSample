package com.ys.player.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.media3.common.Player
import androidx.media3.common.Player.RepeatMode
import androidx.media3.exoplayer.ExoPlayer

@Composable
fun rememberManagedExoPlayer(
    playWhenReady: Boolean = true,
    @RepeatMode repeatMode: Int = Player.REPEAT_MODE_ALL
): State<Player?> = rememberManagedPlayer { context ->
    val builder = ExoPlayer.Builder(context)
    builder.build().apply {
        this.playWhenReady = playWhenReady
        this.repeatMode = repeatMode
    }
}
