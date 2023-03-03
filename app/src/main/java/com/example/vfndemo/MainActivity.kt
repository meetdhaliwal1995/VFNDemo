package com.example.vfndemo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.vfndemo.Utils.ExoPlayerColumnAutoplayScreen
import com.example.vfndemo.Utils.AutoVideoPlayer.UserVideo
import com.example.vfndemo.Utils.CoinDetails.CoinGraphActivity
import com.example.vfndemo.Utils.CoinDetails.FragmentCoin
import com.example.vfndemo.Utils.CoinDetails.GraphModel
import com.example.vfndemo.Utils.CoinDetails.Model.DataGraph
import com.example.vfndemo.Utils.CoinDetails.Model.UserTimeLineModel
import com.example.vfndemo.Utils.LineChartActivity
import com.example.vfndemo.Utils.MergeVideo.MergeVideoActivity
import com.example.vfndemo.ui.theme.VFNDemoTheme
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource

public var videoPath: String? = null
public var instancee: MainActivity? = null

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instancee = this
        videoPath = cacheDir.absolutePath
        setContent {
            VFNDemoTheme {
                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
//                    MainContent()
//                }
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    RecylerView(data = dummyVideoUrl())
//                }
                setContent {
//                    ComposevideoplaybackTheme {
//                        ProvideWindowInsets {
                    Surface {
                        MainContent()
//                            }
                    }
//                    }
                }
            }
        }


    }

    companion object {
        lateinit var instance: MainActivity
            private set
    }

    @Composable
    fun MainContent() {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text("Auto Play Video", color = Color.White)
//            Image(
//                painterResource(R.drawable.ic_play),
//                contentDescription = "",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 150.dp)
//                    .clickable {
//                        val intent = Intent(this, CoinGraphActivity::class.java)
//                        startActivity(intent)
//                    }
//            )
                    Text(
                        text = "Open Graph",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 100.dp)
                            .clickable {
                                val intent = Intent(this, MergeVideoActivity::class.java)
                                startActivity(intent)
                            }
                    )

                }, backgroundColor = Color(0xff0f9d58), modifier = Modifier.clickable {
//            this.startActivity(Intent(this, CoinGraphActivity::class.java))
                })
            },
            content = { ExoPlayerColumnAutoplayScreen() }

        )
    }

    @Composable
    fun RecylerView(data: List<UserVideo>) {

        val stateC = rememberLazyListState()
//    val stateC = rememberLazyListState().layoutInfo.visibleItemsInfo.size

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = stateC,
        ) {
            items(data) { url ->
                Column(modifier = Modifier
                    .fillParentMaxHeight()
                    .onSizeChanged {
                        Log.e("POE", it.toString())

                    }
                    .onGloballyPositioned() {
                        Log.e("POSS", stateC.layoutInfo.visibleItemsInfo[0].index.toString())
                        Log.e(
                            "POS",
                            it
                                .positionInParent()
                                .toString()
                        )
                    }) {
                    Greeting(userVideo = url)
                }
            }
        }
    }
}

@Composable
fun VideoVieww(url: UserVideo) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
////        backgroundColor = Color.Red
//    ) {
//        Row() {
//            Text(text = "Manjitt")
//        }
//
//            val mContext = LocalContext.current
//            var player = SimpleExoPlayer.Builder(mContext).build()
//            val playerView = PlayerView(mContext)
//            val mediaitem = MediaItem.fromUri(url.videoUrl)
//
//            val playWhenReady by rememberSaveable {
//                mutableStateOf(true)
//            }
//
//
//            player.setMediaItem(mediaitem)
//            playerView.player = player
//
//            LaunchedEffect(player) {
//                player.prepare()
//                player.playWhenReady = playWhenReady
//            }
//
//
//            Box(modifier = Modifier) {
//                DisposableEffect(key1 = Unit) { onDispose { player.release() } }
//
//                AndroidView(factory = {
//                    playerView
//                })
//            }
//    }
}

@Composable
fun VideoView(url: UserVideo) {
    val context = LocalContext.current

    Button(
        onClick = { /* Do something */ },
        // Assign reference "button" to the Button composable
        // and constrain it to the top of the ConstraintLayout
        modifier = Modifier
            .background(Color.Blue)
            .fillMaxWidth()
    )

    {
        Text(url.name)
    }


    val exoPlayer = ExoPlayer.Builder(LocalContext.current)
        .build()
        .also { exoPlayer ->
            val mediaItem = MediaItem.Builder()
                .setUri(url.videoUrl)
                .build()
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = false
            exoPlayer.repeatMode = Player.REPEAT_MODE_ALL
            exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
            StyledPlayerView(context).apply {
                hideController()
                useController = false

                player = exoPlayer
            }
        }

    DisposableEffect(
        AndroidView(factory = {
            StyledPlayerView(context).apply {
                player = exoPlayer
            }
        })
    ) {
        onDispose {
            exoPlayer.release()

        }
    }
}

@Composable
//@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun VideoPlayer(url: UserVideo) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val defaultDataSourceFactory = DefaultDataSource.Factory(context)
                val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(
                    context,
                    defaultDataSourceFactory
                )
                val source = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(url.videoUrl))

                setMediaSource(source)
                prepare()
            }
    }

    exoPlayer.playWhenReady = true
    exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    exoPlayer.repeatMode = Player.REPEAT_MODE_ONE

    DisposableEffect(
        AndroidView(factory = {
            PlayerView(context).apply {
                hideController()
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            }
        })
    ) {
        onDispose { exoPlayer.release() }
    }
}

@Composable
fun Greeting(userVideo: UserVideo) {
    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context).build()
    val mediaItem = MediaItem.fromUri(Uri.parse(userVideo.videoUrl))
    exoPlayer.setMediaItem(mediaItem)


    val playerView = StyledPlayerView(context)
    playerView.player = exoPlayer

    DisposableEffect(
        AndroidView(factory = {
            playerView
        })
    ) {
        exoPlayer.prepare()
        exoPlayer.playWhenReady = false
        onDispose {
            exoPlayer.release()

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VFNDemoTheme {
//       MainContent()
    }
}
