package com.example.vfndemo.Utils.MergeVideo

import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.StructuredName
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class ActivityExoPlayerJetpack: ComponentActivity() {

    private var path: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        path = intent.extras?.getString("outputPath")

        setContent {
            // Calling the composable function
            // to display element and its contents
            MainContent()
        }
    }

    @Composable
    fun MainContent() {
        Scaffold(
            topBar = { TopAppBar(title = { Text("GFG | ExoPlayer Video", color = Color.White) }, backgroundColor = Color(0xff0f9d58)) },
            content = { MyContent() }
        )
    }

    @Composable
    fun MyContent(){

//        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//
//            // Fetching the Local Context
//            val mContext = LocalContext.current
//
////            val mVideoUrl = "https://cdn.videvo.net/videvo_files/video/free/2020-05/large_watermarked/3d_ocean_1590675653_preview.mp4"
//            val mVideoUrl = path
//
//            // Declaring ExoPlayer
//            val mExoPlayer = remember(mContext) {
//                ExoPlayer.Builder(mContext).build().apply {
//                    val dataSourceFactory = DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, mContext.packageName))
//                    val source = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mVideoUrl))
//                    prepare(source)
//                }
//            }
//
//            // Implementing ExoPlayer
//            AndroidView(factory = { context ->
//                PlayerView(context).apply {
//                    player = mExoPlayer
//                }
//            })
//        }
    }
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MainContent()
    }
}