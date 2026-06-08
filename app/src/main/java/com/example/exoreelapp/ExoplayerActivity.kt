package com.example.exoreelapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class ExoplayerActivity : AppCompatActivity() {

    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView
    private lateinit var titleView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reel)

        playerView = findViewById(R.id.pv)
        titleView = findViewById(R.id.title)
        
        val title = intent.getStringExtra("VIDEO_TITLE") ?: "Reel"
        titleView.text = title

        initializePlayer()
    }

    private fun initializePlayer() {
        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        val videoResId = intent.getIntExtra("VIDEO_RES_ID", 0)
        if (videoResId != 0) {
            val mediaItem = MediaItem.fromUri("android.resource://${packageName}/$videoResId")
            player?.setMediaItem(mediaItem)
            player?.prepare()
            player?.playWhenReady = true
        }
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }
}
