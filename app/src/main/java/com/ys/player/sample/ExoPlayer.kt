package com.ys.player.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

@Composable
fun rememberManagedExoPlayer(): State<Player?> = rememberManagedPlayer { context ->
    val builder = ExoPlayer.Builder(context)
    builder.build().apply {
        playWhenReady = true
    }
}
