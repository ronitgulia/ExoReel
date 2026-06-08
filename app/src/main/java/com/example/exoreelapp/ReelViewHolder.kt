package com.example.exoreelapp

import android.view.View
import android.widget.TextView
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView

class ReelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val playerView: PlayerView = itemView.findViewById(R.id.pv)
    val title: TextView = itemView.findViewById(R.id.title)
}
