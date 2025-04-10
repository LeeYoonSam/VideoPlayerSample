package com.ys.player.sample

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.navigation.NavHostController
import com.ys.player.media.Media
import com.ys.player.media.ResizeMode
import com.ys.player.media.ShowBuffering
import com.ys.player.media.SurfaceType
import com.ys.player.media.rememberMediaState

private enum class ControllerType {
    None, Simple, PlayerControlView
}

private val ControllerTypes = ControllerType.entries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Basic(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Basic") },
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
        BasicContent(
            Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}

@Composable
fun BasicContent(
    modifier: Modifier = Modifier
) {
    var url by rememberSaveable { mutableStateOf(Urls[0]) }

    var surfaceType by rememberSaveable { mutableStateOf(SurfaceType.SurfaceView) }
    var resizeMode by rememberSaveable { mutableStateOf(ResizeMode.Fit) }
    var keepContentOnPlayerReset by rememberSaveable { mutableStateOf(false) }
    var useArtwork by rememberSaveable { mutableStateOf(true) }
    var showBuffering by rememberSaveable { mutableStateOf(ShowBuffering.Always) }

    var setPlayer by rememberSaveable { mutableStateOf(true) }
    var playWhenReady by rememberSaveable { mutableStateOf(true) }

    var controllerHideOnTouch by rememberSaveable { mutableStateOf(true) }
    var controllerAutoShow by rememberSaveable { mutableStateOf(true) }
    var controllerType by rememberSaveable { mutableStateOf(ControllerType.Simple) }

    var rememberedMediaItemIdAndPosition: Pair<String, Long>? by remember { mutableStateOf(null) }
    val player by rememberManagedExoPlayer()
    DisposableEffect(player, playWhenReady) {
        player?.playWhenReady = playWhenReady
        onDispose {}
    }

    val mediaItem = remember(url) { MediaItem.Builder().setMediaId(url).setUri(url).build() }
    DisposableEffect(mediaItem, player) {
        player?.run {
            setMediaItem(mediaItem)
            rememberedMediaItemIdAndPosition?.let { (id, position) ->
                if (id == mediaItem.mediaId) seekTo(position)
            }?.also { rememberedMediaItemIdAndPosition = null }
            prepare()
        }
        onDispose {}
    }

    val state = rememberMediaState(player = player.takeIf { setPlayer })
    val content = remember {
        movableContentOf {
            Media(
                state,
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .background(Color.Black),
                surfaceType = surfaceType,
                resizeMode = resizeMode,
                keepContentOnPlayerReset = keepContentOnPlayerReset,
                useArtwork = useArtwork,
                showBuffering = showBuffering,
                buffering = {
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        CircularProgressIndicator()
                    }
                },
                errorMessage = { error ->
                    Box(Modifier.fillMaxSize(), Alignment.Center) {
                        Text(
                            error.message ?: "",
                            modifier = Modifier
                                .background(Color(0x80808080), RoundedCornerShape(16.dp))
                                .padding(horizontal = 12.dp, vertical = 4.dp),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                controllerHideOnTouch = controllerHideOnTouch,
                controllerAutoShow = controllerAutoShow,
                controller = when (controllerType) {
                    ControllerType.None -> null
                    ControllerType.Simple -> @Composable { state ->
                        SimpleController(state, Modifier.fillMaxSize())
                    }
                    ControllerType.PlayerControlView -> @Composable { state ->
                        PlayerControlViewController(state, Modifier.fillMaxSize())
                    }
                }
            )
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Option("Url", Urls, url) { url = it }
                Option("Surface Type", SurfaceTypes, surfaceType) { surfaceType = it }
                Option("Resize Mode", ResizeModes, resizeMode) { resizeMode = it }
                BooleanOption(
                    "Keep Content On Player Reset",
                    keepContentOnPlayerReset
                ) { keepContentOnPlayerReset = it }
                BooleanOption("Use Artwork", useArtwork) { useArtwork = it }
                Option("Show Buffering", ShowBufferingValues, showBuffering) { showBuffering = it }
                BooleanOption("Set Player", setPlayer) { setPlayer = it }
                Column(Modifier.padding(start = 18.dp)) {
                    BooleanOption("Play When Ready", playWhenReady) { playWhenReady = it }
                }
                Option("Controller", ControllerTypes, controllerType) { controllerType = it }
                Column(Modifier.padding(start = 18.dp)) {
                    val enabled = controllerType != ControllerType.None
                    BooleanOption(
                        "Hide On Touch",
                        controllerHideOnTouch,
                        enabled
                    ) { controllerHideOnTouch = it }
                    BooleanOption("Auto Show", controllerAutoShow, enabled) {
                        controllerAutoShow = it
                    }
                }
            }
        }
    }

    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    if (!isLandscape) Column(modifier) { content() } else Row(modifier) { content() }
}
