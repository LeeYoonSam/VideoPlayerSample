package com.ys.player.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VideoPlayerControls(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    currentPosition: Long,
    duration: Long,
    isMuted: Boolean,
    onPlayPause: () -> Unit,
    onSeek: (Long) -> Unit,
    onMuteToggle: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Slider(
            value = currentPosition.toFloat(),
            onValueChange = { onSeek(it.toLong()) },
            valueRange = if (duration > 0) 0f..duration.toFloat() else 0f..1f,
            modifier = Modifier.fillMaxWidth()
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onPlayPause) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Clear else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play"
                )
            }
            
            IconButton(onClick = onMuteToggle) {
                Icon(
                    imageVector = if (isMuted) Icons.Default.Lock else Icons.Default.AddCircle,
                    contentDescription = if (isMuted) "Unmute" else "Mute"
                )
            }
        }
    }
}