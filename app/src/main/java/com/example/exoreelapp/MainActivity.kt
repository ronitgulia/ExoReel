package com.example.exoreelapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private val data = mutableListOf<Anime>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        data.add(Anime("Reel 1", R.raw.video_1, R.raw.video_1))
        data.add(Anime("Reel 2", R.raw.video_2, R.raw.video_2))
        data.add(Anime("Reel 3", R.raw.video_3, R.raw.video_3))
        data.add(Anime("Reel 4", R.raw.video_4, R.raw.video_4))
        data.add(Anime("Reel 5", R.raw.video_5, R.raw.video_5))

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.adapter = ReelAdapter(data)
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
    }
}
