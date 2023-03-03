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
import androidx.media3.ui.PlayerView
import com.example.vfndemo.R

@Composable
fun VideoPlayer(
    exoPlayer: androidx.media3.exoplayer.ExoPlayer,
    onControllerVisibilityChanged: (uiVisible: Boolean) -> Unit
) {
    val context = LocalContext.current
    val playerView = remember {
        val layout = LayoutInflater.from(context).inflate(R.layout.video_player, null, false)
        val playerView = layout.findViewById(R.id.playerView) as PlayerView
        playerView.apply {
            setControllerVisibilityListener(PlayerView.ControllerVisibilityListener {
                onControllerVisibilityChanged(it == View.VISIBLE)
            })
            player = exoPlayer
        }
    }

    AndroidView(
        { playerView },
        Modifier
            .fillMaxSize()
            .height(700.dp)
            .background(Color.Red)
    )
}