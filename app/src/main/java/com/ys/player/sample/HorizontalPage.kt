package com.ys.player.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.navigation.NavHostController
import com.ys.player.simple.media.MediaSimple
import com.ys.player.simple.media.ResizeMode
import com.ys.player.simple.media.rememberMediaState
import com.ys.player.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HorizontalPager") },
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
        HorizontalPageContent(Modifier.fillMaxSize(), padding)
    }
}

@Composable
fun HorizontalPageContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    val mediaItems = remember { Urls.map { MediaItem.Builder().setMediaId(it).setUri(it).build() } }
    val player by rememberManagedExoPlayer()

    val pagerState = rememberPagerState(pageCount = { mediaItems.size })
    val currentPage = pagerState.currentPage

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxWidth().height(361.dp),
        pageSize = PageSize.Fixed(156.dp),
        contentPadding = contentPadding,
        pageSpacing = 12.dp
    ) { page ->
        val mediaItem = mediaItems[page]
        val isCurrentlyVisible = page == currentPage && !pagerState.isScrollInProgress

        Column(
            modifier = Modifier
                .width(156.dp)
                .height(361.dp)
                .background(Color.White)
        ) {
            Box {
                PagerItem(
                    showVideo = isCurrentlyVisible
                ) {
                    LaunchedEffect(mediaItem, player) {
                        player?.run {
                            setMediaItem(mediaItem)
                            prepare()
                        }
                    }
                    MediaSimple(
                        state = rememberMediaState(player = player),
                        resizeMode = ResizeMode.Fill,
                        modifier = Modifier
                            .matchParentSize()
                    )
                }

                Box(modifier = Modifier.padding(top = 6.dp, start = 6.dp)) {
                    Row(modifier = Modifier
                        .wrapContentSize()
                        .background(color = Color(0x99000000), shape = RoundedCornerShape(size = 1000.dp))
                        .padding(6.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(12.dp),
                            imageVector = Icons.Outlined.Face,
                            tint = Color.White,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "videoItem.viewCount",
                            style = Typography.labelSmall.copy(color = Color.White),
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(horizontal = 4.dp, vertical = 10.dp)) {
                Text(
                    text = "videoItem.description",
                    style = Typography.bodyMedium.copy(color = Color.Black),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun PagerItem(
    showVideo: Boolean,
    video: @Composable BoxScope.() -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(12.dp))
    ) {
        if (showVideo) {
            video()
        }
    }
}