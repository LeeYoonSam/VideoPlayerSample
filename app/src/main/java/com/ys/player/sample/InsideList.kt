package com.ys.player.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.navigation.NavHostController
import com.ys.player.media.Media
import com.ys.player.media.rememberMediaState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsideList(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inside List") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) { padding ->
        InsideListContent(Modifier.fillMaxSize(), padding)
    }
}

@Composable
fun InsideListContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    val mediaItems = remember { Urls.map { MediaItem.Builder().setMediaId(it).setUri(it).build() } }
    val player by rememberManagedExoPlayer()

    val listState = rememberLazyListState()
    val focusedIndex by remember(listState) {
        derivedStateOf {
            val firstVisibleItemIndex = listState.firstVisibleItemIndex
            val firstVisibleItemScrollOffset = listState.firstVisibleItemScrollOffset
            if (firstVisibleItemScrollOffset == 0) {
                firstVisibleItemIndex
            } else if (firstVisibleItemIndex + 1 <= listState.layoutInfo.totalItemsCount - 1) {
                firstVisibleItemIndex + 1
            } else -1
        }
    }
    LazyColumn(
        modifier = modifier,
        state = listState,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = contentPadding,
    ) {
        itemsIndexed(mediaItems) { index, mediaItem ->
            ListItem(
                showVideo = focusedIndex == index
            ) {
                LaunchedEffect(mediaItem, player) {
                    player?.run {
                        setMediaItem(mediaItem)
                        prepare()
                    }
                }
                Media(
                    state = rememberMediaState(player = player),
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black)
                )
            }
        }
    }
}

@Composable
fun ListItem(
    showVideo: Boolean,
    video: @Composable BoxScope.() -> Unit
) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.aspectRatio(1f)) {
            if (showVideo) {
                video()
            }
        }
    }
}
