package com.example.vfndemo.Utils.AutoVideoPlayer


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.exoplayer.ExoPlayer
import coil.compose.rememberImagePainter
import com.example.vfndemo.R
import com.example.vfndemo.Utils.ExoPlayerColumnAutoplayScreen

@Composable
fun AutoPlayVideoCard(
    videoItem: VideoItem,
    isPlaying: Boolean,
    exoPlayer: ExoPlayer,
) {
    Box(
        modifier = Modifier
            .height(700.dp)
            .fillMaxWidth()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        if (isPlaying) {
            VideoPlayerWithControls(exoPlayer)
        } else {
            VideoThumbnail(videoItem.thumbnail)
        }

        if (!isPlaying) {

            Image(
                painter = rememberImagePainter(videoItem.thumbnail),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
            )
        }

        Text(
            style = TextStyle(color = Color.Red),
            text = videoItem.name,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 8.dp, bottom = 55.dp)
        )

        Text(
            style = TextStyle(color = Color.Red),
            text = videoItem.mediaUrl,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 8.dp, bottom = 20.dp)
        )

//        Text(
//            style = TextStyle(color = Color.Red),
//            text = "Save Video",
//            modifier = Modifier
//                .align(Alignment.BottomStart)
//                .padding(start = 8.dp, bottom = 100.dp)
//        )
    }
}