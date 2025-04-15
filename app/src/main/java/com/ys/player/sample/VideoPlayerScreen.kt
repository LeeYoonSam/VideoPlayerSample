package com.ys.player.sample

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ys.player.common.PlayerEvent
import com.ys.player.common.VideoPlayerView
import com.ys.player.common.VideoPlayerViewModel
import com.ys.player.simple.media.sampleVideoItemViewModels

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoPlayerScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Video Player") },
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
        VideoPlayerSample(Modifier.fillMaxSize(), padding)
    }
}

@Composable
fun VideoPlayerSample(
    modifier: Modifier,
    paddingValues: PaddingValues
) {
    val viewModel: VideoPlayerViewModel = viewModel()
    val videoInfo = sampleVideoItemViewModels[2]

    VideoPlayerView(
        viewModel = viewModel,    
        videoUri = videoUrls[0],
        modifier = modifier,
        thumbnailUrl = videoInfo.thumbnailUrl,
        autoPlay = false,
        isMuted = true,
        isShowController = true
    )
}