package com.example.vfndemo.Utils.AutoVideoPlayer

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import android.R
import androidx.compose.foundation.layout.*
import com.example.vfndemo.Utils.OnClick

@SuppressLint("ResourceType")
@Composable
fun VideoCard(
    modifier: Modifier = Modifier,
    videoItem: VideoItem,
    isPlaying: Boolean,
    exoPlayer: androidx.media3.exoplayer.ExoPlayer,
    onClick: OnClick
) {
    var isPlayerUiVisible by remember { mutableStateOf(false) }
    val isPlayButtonVisible = if (isPlayerUiVisible) true else !isPlaying

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black,),
//            .clip(Shapes),
        contentAlignment = Alignment.Center
    ) {
        if (isPlaying) {
            VideoPlayer(exoPlayer) { uiVisible ->
                isPlayerUiVisible = when {
                    isPlayerUiVisible -> uiVisible
                    else -> true
                }
            }
        } else {
            VideoThumbnail(videoItem.thumbnail)
        }
        if (isPlayButtonVisible) {
            Icon(
                painter = painterResource(if (isPlaying) R.drawable.ic_media_pause else R.drawable.ic_media_play),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(72.dp)
                    .clip(remember { RoundedCornerShape(percent = 50) })
                    .clickable(onClick = onClick)
            )
        }

    }
}