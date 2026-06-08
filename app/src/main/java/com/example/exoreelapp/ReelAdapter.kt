package com.example.exoreelapp

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView

class ReelAdapter(private val data: List<Anime>) : RecyclerView.Adapter<ReelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reel, parent, false)
        return ReelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReelViewHolder, position: Int) {
        val anime = data[position]
        val context = holder.itemView.context
        
        Log.d("ReelAdapter", "Binding position $position: ${anime.title}, resId: ${anime.reel}")
        
        // Release existing player if any to avoid leaks
        holder.playerView.player?.release()
        
        val player = ExoPlayer.Builder(context).build()
        holder.playerView.player = player

        // Try raw resource URI format
        val mediaUri = Uri.parse("android.resource://${context.packageName}/${anime.reel}")
        val mediaItem = MediaItem.fromUri(mediaUri)
        holder.title.text = anime.title
        
        player.setMediaItem(mediaItem)
        player.prepare()
        player.repeatMode = ExoPlayer.REPEAT_MODE_ALL
        player.playWhenReady = true
        
        player.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                Log.e("ReelAdapter", "Player Error at position $position: ${error.message}", error)
            }
            override fun onPlaybackStateChanged(state: Int) {
                val stateName = when(state) {
                    Player.STATE_IDLE -> "IDLE"
                    Player.STATE_BUFFERING -> "BUFFERING"
                    Player.STATE_READY -> "READY"
                    Player.STATE_ENDED -> "ENDED"
                    else -> "UNKNOWN"
                }
                Log.d("ReelAdapter", "Playback state changed at position $position: $stateName")
            }
        })
        
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ExoplayerActivity::class.java)
            intent.putExtra("VIDEO_RES_ID", anime.video)
            intent.putExtra("VIDEO_TITLE", anime.title)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onViewRecycled(holder: ReelViewHolder) {
        super.onViewRecycled(holder)
        holder.playerView.player?.release()
        holder.playerView.player = null
    }
}
