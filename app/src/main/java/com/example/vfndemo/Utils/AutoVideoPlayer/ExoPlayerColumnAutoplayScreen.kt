package com.example.vfndemo.Utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import coil.compose.rememberImagePainter
import com.example.vfndemo.MainActivity
import com.example.vfndemo.Media
import com.example.vfndemo.Utils.AutoVideoPlayer.AutoPlayVideoCard
import com.example.vfndemo.Utils.AutoVideoPlayer.ExoPlayerColumnAutoplayViewModel
import com.example.vfndemo.Utils.AutoVideoPlayer.VideoItem
import com.example.vfndemo.videoPath
import kotlin.math.abs

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ExoPlayerColumnAutoplayScreen(viewModel: ExoPlayerColumnAutoplayViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val listState = rememberLazyListState()
    var isVisible by remember { mutableStateOf(true) }
    var video1:  String? = null
    var video2:  String? = null
    var myDownload: Long? = null


    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            addListener(object : Player.Listener {
                override fun onRenderedFirstFrame() {
//                    Handler(Looper.getMainLooper()).postDelayed({
                    if (isVisible) {
                        isVisible = false

                    }
//                    }, 500)
                }
            })
        }
    }
    val videos by viewModel.videos.collectAsStateWithLifecycle()
    var playingVideoItem by remember { mutableStateOf(videos.firstOrNull()) }


    LaunchedEffect(Unit) {
        snapshotFlow {
            listState.playingItem(videos)
        }.collect { videoItem ->
            Log.e("SDS", exoPlayer.currentPosition.toString())
            playingVideoItem?.currentPosition = exoPlayer.currentPosition
            playingVideoItem = videoItem
        }
    }



    LaunchedEffect(playingVideoItem) {
//        playingVideoItem = videos[playingVideoItem!!.id - 1]

        if (playingVideoItem == null) {
            exoPlayer.pause()
        } else {
            if (playingVideoItem!!.id > 1) {
//                videos[playingVideoItem!!.id - 2].currentPosition = exoPlayer.currentPosition
//                Log.e("ASDS",  videos[playingVideoItem!!.id - 2].currentPosition.toString())
            }

            // move playWhenReady to exoPlayer initialization if you don't
            // want to play next video automatically
//            Handler(Looper.getMainLooper()).post {

            isVisible = true
            exoPlayer.setMediaItem(MediaItem.fromUri(playingVideoItem!!.mediaUrl))
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
            exoPlayer.repeatMode = ExoPlayer.REPEAT_MODE_ONE
            exoPlayer.seekTo(playingVideoItem!!.currentPosition)
        }
    }

    DisposableEffect(exoPlayer) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (playingVideoItem == null) return@LifecycleEventObserver
            when (event) {
                Lifecycle.Event.ON_START -> {
                    exoPlayer.play()
                }
                Lifecycle.Event.ON_STOP -> {
                    exoPlayer.pause()
                }

                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            exoPlayer.release()
        }

    }

//    Column {
//        val listState = rememberLazyListState()
//        val topOfListProgress: Float by remember {
//            derivedStateOf {
//                listState.run {
//                    if (firstVisibleItemIndex > 0) {
//                        0f
//                    } else {
//                        layoutInfo.visibleItemsInfo
//                            .firstOrNull()
//                            ?.let {
//                                1f - firstVisibleItemScrollOffset.toFloat() / it.size
//                            } ?: 1f
//                    }
//                }
//            }
//        }
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = 8.dp, end = 8.dp)
//            .background(color = Color.LightGray)
//            .layout { measurable, constraints ->
//                val placeable = measurable.measure(constraints)
//                val offset = (placeable.height * (1f - topOfListProgress)).roundToInt()
//                layout(placeable.width, placeable.height - offset) {
//                    placeable.place(0, -offset)
//                }
//            }
//            .alpha(topOfListProgress)
//    ) {
//        Text("Open Coin Graph Activity", modifier = Modifier.padding(vertical = 8.dp)
//            .fillMaxWidth()
//            .clickable{
//            Log.e("RUNNNN", "Open ")
//                context.startActivity(Intent(context, CoinGraphActivity::class.java))
//            })
//    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
        contentPadding = WindowInsets.systemBars
            .only(WindowInsetsSides.Vertical)
            .add(WindowInsets(left = 10.dp, right = 10.dp, bottom = 8.dp))
            .asPaddingValues()
    )
    {
        items(videos, VideoItem::id) { video ->
            Spacer(modifier = Modifier.height(6.dp))

            Card() {
                AutoPlayVideoCard(
                    videoItem = video,
                    exoPlayer = exoPlayer,
                    isPlaying = video.id == playingVideoItem?.id,
                )

                Text(
                    style = TextStyle(color = Color.Red),
                    text = "Save Video",
                    modifier = Modifier
                        .padding(start = 8.dp, top = 20.dp)
                        .clickable {
//                            videoPath?.let { it1 -> viewModel.saveVideo(it1, video.id)



                                viewModel.downloadFileToCacheAndGallery(video.mediaUrl, Media.ContentType.VIDEO)
                                Log.e("SHOWWWWW", "VIDEO SAVE")

//                            video1 = videoPath.toString()
//                            if (video1.isNullOrEmpty()){
//                                video1 = videoPath.toString()
//                            }else{
//                                video2 = videoPath.toString()
//                            }

                        }
                )

                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(
                        // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                        initialAlpha = 0.4f
                    ),
                    exit = fadeOut(
                        // Overwrites the default animation with tween
                        animationSpec = tween(durationMillis = 150)
                    )
                ) {
                    // Content that needs to appear/disappear goes here:
                    Image(
                        painter = rememberImagePainter(video.thumbnail),
                        contentDescription = null,
                        modifier = Modifier
                            .size(700.dp)
                    )
                }
            }
        }
    }


    @Composable
    fun AddImage() {
        Image(
            painter = rememberImagePainter(playingVideoItem?.thumbnail),
            contentDescription = null,
            modifier = Modifier.size(700.dp)
        )
    }


}


private fun LazyListState.playingItem(videos: List<VideoItem>): VideoItem? {
    if (layoutInfo.visibleItemsInfo.isEmpty() || videos.isEmpty()) return null
    val layoutInfo = layoutInfo
    val visibleItems = layoutInfo.visibleItemsInfo
    val lastItem = visibleItems.last()
    val firstItemVisible = firstVisibleItemIndex == 0 && firstVisibleItemScrollOffset == 0
    val itemSize = lastItem.size
    val itemOffset = lastItem.offset
    val totalOffset = layoutInfo.viewportEndOffset
    val lastItemVisible = lastItem.index == videos.size - 1 && totalOffset - itemOffset >= itemSize
    val midPoint = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
    val centerItems = visibleItems.sortedBy { abs(it.offset + it.size / 2 - midPoint) }

    return when {
        firstItemVisible -> videos.first()
        lastItemVisible -> videos.last()
        else -> centerItems.firstNotNullOf { videos[it.index] }
    }
}



//var request = DownloadManager.Request(Uri.parse(video.mediaUrl))
//request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"fileName.mp4")
//request.allowScanningByMediaScanner()
//request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//var dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//myDownload = dm.enqueue(request)
//request.setDestinationInExternalPublicDir(context.applicationContext.toString(), "/file", "Question")