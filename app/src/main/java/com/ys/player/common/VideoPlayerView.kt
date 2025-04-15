package com.ys.player.common

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil3.compose.AsyncImage

@Composable
fun VideoPlayerView(
    viewModel: VideoPlayerViewModel,
    videoUri: String,
    modifier: Modifier = Modifier,
    thumbnailUrl: String? = null,
    autoPlay: Boolean = false,
    isMuted: Boolean = false,
    isShowController: Boolean = false,
    loopDuration: Long? = null
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    val playerState by viewModel.playerState.collectAsState()

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItemBuilder = MediaItem.Builder()
                .setUri(videoUri)
            
            if (loopDuration != null) {
                mediaItemBuilder.setClippingConfiguration(
                    MediaItem.ClippingConfiguration.Builder()
                        .setStartPositionMs(0)
                        .setEndPositionMs(loopDuration)
                        .setRelativeToDefaultPosition(true)
                        .build()
                )
            }
            
            val mediaItem = mediaItemBuilder.build()
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = autoPlay
            volume = if (isMuted) 0f else 1f
        }
    }

    LaunchedEffect(playerState) {
        if (playerState.isPlaying) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }
    
    val playerView = remember {
        PlayerView(context).apply {
            player = exoPlayer
            layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            useController = false
        }
    }
    
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer.pause()
                    viewModel.onEvent(PlayerEvent.Pause)
                }
                Lifecycle.Event.ON_RESUME -> {
                    if (autoPlay) {
                        exoPlayer.play()
                        viewModel.onEvent(PlayerEvent.Play)
                    }
                }
                else -> {}
            }
        }
        
        lifecycleOwner.lifecycle.addObserver(observer)
        
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
        }
    }
    
    // Loop duration handling
    LaunchedEffect(loopDuration) {
        viewModel.onEvent(PlayerEvent.SetLoopDuration(loopDuration))
    }
    
    // Player state updates
    LaunchedEffect(exoPlayer) {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_READY -> {
                        viewModel.updatePlayerState(
                            isPlaying = exoPlayer.isPlaying,
                            isBuffering = false,
                            currentPosition = exoPlayer.currentPosition,
                            duration = exoPlayer.duration,
                            volume = exoPlayer.volume,
                            isMuted = exoPlayer.volume == 0f
                        )
                    }
                    Player.STATE_BUFFERING -> {
                        viewModel.updatePlayerState(isBuffering = true)
                    }
                    Player.STATE_ENDED -> {
                        if (autoPlay) {
                            exoPlayer.seekTo(0)
                            exoPlayer.play()
                            viewModel.onEvent(PlayerEvent.Play)
                        } else {
                            exoPlayer.seekTo(0)
                            viewModel.onEvent(PlayerEvent.Pause)
                        }
                    }
                }
            }
            
            override fun onPlayerError(error: PlaybackException) {
                viewModel.updatePlayerState(error = error.message)
            }
        })
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            factory = { playerView },
            modifier = Modifier.fillMaxSize()
        )

        if (thumbnailUrl != null && !playerState.isPlaying) {
            AsyncImage(
                model = thumbnailUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        if (isShowController) {
            VideoPlayerControls(
                isPlaying = playerState.isPlaying,
                currentPosition = playerState.currentPosition,
                duration = playerState.duration,
                isMuted = playerState.isMuted,
                onPlayPause = {
                    if (exoPlayer.isPlaying) {
                        exoPlayer.pause()
                        viewModel.onEvent(PlayerEvent.Pause)
                    } else {
                        exoPlayer.play()
                        viewModel.onEvent(PlayerEvent.Play)
                    }
                },
                onSeek = { position ->
                    exoPlayer.seekTo(position)
                    viewModel.onEvent(PlayerEvent.SeekTo(position))
                },
                onMuteToggle = {
                    val newMutedState = !playerState.isMuted
                    exoPlayer.volume = if (newMutedState) 0f else 1f
                    viewModel.onEvent(PlayerEvent.ToggleMute)
                }
            )
        }
    }
}
