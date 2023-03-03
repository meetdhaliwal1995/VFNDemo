package com.example.vfndemo.Utils.MergeVideo

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.vfndemo.databinding.MainActivityBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView

class ActivityExoPlayer : AppCompatActivity(), Player.Listener {
    private var _binding: MainActivityBinding? = null
    private val binding get() = _binding
    private lateinit var player: ExoPlayer
    private lateinit var playerView: StyledPlayerView
    private var path: String?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        path = intent.extras?.getString("outputPath")
//        path = "/storage/emulated/0/Android/data/com.example.vfndemo/files/Movies/MarvelEditor20221117_211652_2002747955621951656.mp4"
        Log.e("getPathhh", path.toString())
        setUpPlayer()
        addMp4Files()

//        val fragmentTransaction = fragmentManager?.beginTransaction()
//        fragmentTransaction?.replace(R.id.frame_container, OptiMergeFragment())?.commit()
//        binding?.ibGallery?.setOnClickListener {
//            val fragmentTransaction = fragmentManager?.beginTransaction()
//            fragmentTransaction?.replace(R.id.frame_container, OptiMergeFragment())?.commit()


    }

    private fun setUpPlayer() {
        player = ExoPlayer.Builder(this).build()
        binding?.exoPlayer?.player = player
        player.addListener(this)
    }

    private fun addMp4Files(){
        val mediaItem = path?.let { MediaItem.fromUri(it) }
        mediaItem?.let { player.addMediaItem(it) }
        player.prepare()
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
    }

    private fun playExoplayer(){
//        try {
//            // bandwidthmeter is used for
//            // getting default bandwidth
//            val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
//
//            // track selector is used to navigate between
//            // video using a default seekbar.
//            val trackSelector: TrackSelector =
//                DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
//
//            // we are adding our track selector to exoplayer.
//            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
//
//            // we are parsing a video url
//            // and parsing its video uri.
//            val videoURI: Uri = Uri.parse(path)
//
//            // we are creating a variable for datasource factory
//            // and setting its user agent as 'exoplayer_view'
//            val dataSourceFactory: DefaultHttpDataSourceFactory =
//                DefaultHttpDataSourceFactory("Exoplayer_video")
//
//            // we are creating a variable for extractor factory
//            // and setting it to default extractor factory.
//            val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory();
//
//            // we are creating a media source with above variables
//            // and passing our event handler as null,
//            val mediaSourse: MediaSource =
//                ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null)
//
//            // inside our exoplayer view
//            // we are setting our player
//            exoPlayerView.player = player
//
//            // we are preparing our exoplayer
//            // with media source.
//            player.prepare(mediaSourse)
//
//            // we are setting our exoplayer
//            // when it is ready.
//            player.playWhenReady = true
//
//
//        } catch (e: Exception) {
//            // on below line we
//            // are handling exception
//            e.printStackTrace()
//        }
    }
}