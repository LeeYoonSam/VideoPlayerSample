package com.ys.player.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.navigation.NavHostController
import com.ys.player.media.Media
import com.ys.player.media.ResizeMode
import com.ys.player.media.ShowBuffering
import com.ys.player.media.rememberMediaState
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
        HorizontalPageContent2(Modifier.fillMaxSize(), padding)
    }
}

@Composable
fun HorizontalPageContent2(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues
) {
    val mediaItems = remember { videoUrls.map { MediaItem.Builder().setMediaId(it).setUri(it).build() } }
    val player by rememberManagedExoPlayer()

    val pagerState = rememberPagerState(pageCount = { mediaItems.size })
    val currentPage = pagerState.currentPage

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxWidth().height(361.dp),
        pageSize = PageSize.Fixed(156.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        pageSpacing = 12.dp
    ) { page ->
        val mediaItem = mediaItems[page]
        val isCurrentlyVisible = page == currentPage && !pagerState.isScrollInProgress

        PagerCardItem(isCurrentlyVisible) {
            LaunchedEffect(mediaItem, player) {
                player?.run {
                    setMediaItem(mediaItem)
                    prepare()
                }
            }
            Media(
                state = rememberMediaState(player = player),
                resizeMode = ResizeMode.Fill,
                showBuffering = ShowBuffering.Never,
                errorMessage = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = it.message.orEmpty(),
                            style = Typography.labelSmall.copy(color = Color.White),
                        )
                    }
                },
                overlay = {
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
                                text = "15,364,346",
                                style = Typography.labelSmall.copy(color = Color.White),
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(237.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}