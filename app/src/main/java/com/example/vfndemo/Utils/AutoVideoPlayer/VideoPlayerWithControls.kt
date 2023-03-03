package com.example.vfndemo.Utils.AutoVideoPlayer

import android.view.LayoutInflater
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.vfndemo.R

@Composable
fun VideoPlayerWithControls(exoPlayer: ExoPlayer) {
    val context = LocalContext.current
    val playerView = remember {
        val layout = LayoutInflater.from(context).inflate(R.layout.video_player, null)
        val playerView = (layout.findViewById(R.id.playerView) as PlayerView).apply {
            player = exoPlayer
            setShutterBackgroundColor(android.graphics.Color.TRANSPARENT)
        }

//        val paue = (layout.findViewById(R.id.video_text) as TextView).apply {


//        }

//        layout.findViewById<ImageButton>(R.id.exo_pause).setOnClickListener { exoPlayer.pause() }
//        layout.findViewById<ImageButton>(R.id.exo_play).setOnClickListener { exoPlayer.play() }
        layout.id = View.generateViewId()
        playerView
    }

    AndroidView(
        { playerView },
        Modifier
            .fillMaxSize()
            .height(700.dp)
            .background(Color.Black),
    )
}