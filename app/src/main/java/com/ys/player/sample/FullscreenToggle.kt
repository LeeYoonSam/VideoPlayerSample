package com.ys.player.sample

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT
import android.content.res.Configuration
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaItem
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ys.player.media.Media
import com.ys.player.media.MediaState
import com.ys.player.media.ShowBuffering
import com.ys.player.media.rememberMediaState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullscreenToggle(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.isStatusBarVisible = !isLandscape
        systemUiController.isNavigationBarVisible = !isLandscape
    }

    val player by rememberManagedExoPlayer()
    val mediaItem = remember { MediaItem.fromUri(Urls[0]) }
    LaunchedEffect(mediaItem, player) {
        player?.run {
            setMediaItem(mediaItem)
            prepare()
        }
    }

    val mediaState = rememberMediaState(player)
    val mediaContent = remember {
        // TODO movableContentOf here doesn't avoid Media from recreating its surface view when
        // screen rotation changed. Seems like a bug of Compose.
        // see: https://kotlinlang.slack.com/archives/CJLTWPH7S/p1654734644676989
        movableContentOf { isLandscape: Boolean, modifier: Modifier ->
            MediaContent(mediaState, isLandscape, modifier)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fullscreen Toggle") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) { padding ->
        if (!isLandscape) {
            mediaContent(
                false,
                Modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )
        }
    }
    if (isLandscape) {
        mediaContent(
            true,
            Modifier
                .fillMaxSize()
                .background(Color.Black)
        )
    }
}

@Composable
private fun MediaContent(
    mediaState: MediaState,
    isLandscape: Boolean,
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current.findActivity()!!
    val enterFullscreen = { activity.requestedOrientation = SCREEN_ORIENTATION_USER_LANDSCAPE }
    val exitFullscreen = {
        @SuppressLint("SourceLockedOrientationActivity")
        // Will reset to SCREEN_ORIENTATION_USER later
        activity.requestedOrientation = SCREEN_ORIENTATION_USER_PORTRAIT
    }
    Box(modifier) {
        Media(
            mediaState,
            modifier = Modifier.fillMaxSize(),
            showBuffering = ShowBuffering.Always,
            buffering = {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }
            },
        )
        Button(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = if (isLandscape) exitFullscreen else enterFullscreen
        ) {
            Text(text = if (isLandscape) "Exit Fullscreen" else "Enter Fullscreen")
        }
    }
    val onBackPressedCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                exitFullscreen()
            }
        }
    }
    val onBackPressedDispatcher = activity.onBackPressedDispatcher
    DisposableEffect(onBackPressedDispatcher) {
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
        onDispose { onBackPressedCallback.remove() }
    }
    SideEffect {
        onBackPressedCallback.isEnabled = isLandscape
        if (isLandscape) {
            if (activity.requestedOrientation == SCREEN_ORIENTATION_USER) {
                activity.requestedOrientation = SCREEN_ORIENTATION_USER_LANDSCAPE
            }
        } else {
            activity.requestedOrientation = SCREEN_ORIENTATION_USER
        }
    }
}
