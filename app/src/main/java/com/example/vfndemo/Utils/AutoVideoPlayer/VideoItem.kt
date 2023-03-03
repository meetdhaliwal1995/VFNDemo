package com.example.vfndemo.Utils.AutoVideoPlayer


import androidx.compose.runtime.Immutable


@Immutable
data class VideoItem(
    val id: Int,
    val mediaUrl: String,
    val thumbnail: String,
    var name: String,
    val lastPlayedPosition: Long = 0,
    var currentPosition:  Long= 0,
)