package com.example.vfndemo.Utils.AutoVideoPlayer;

import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

public class r {

    private void hell(ExoPlayer exoPlayer) {
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onRenderedFirstFrame() {
                Player.Listener.super.onRenderedFirstFrame();
            }
        });
    }
}
