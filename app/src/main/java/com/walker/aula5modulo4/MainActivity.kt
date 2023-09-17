package com.walker.aula5modulo4

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar

class MainActivity : AppCompatActivity() {

    private lateinit var playButton: Button
    private lateinit var pauseButton: Button
    private lateinit var restartButton: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        restartButton = findViewById(R.id.restartButton)
        progressBar = findViewById(R.id.progressBar)

        mediaPlayer = MediaPlayer.create(this, R.raw.sample_sound)

        playButton.setOnClickListener {
            play()
        }

        pauseButton.setOnClickListener {
            pause()
        }

        restartButton.setOnClickListener {
            restart()
        }

        handler = Handler(Looper.getMainLooper())

        runnable = Runnable {
            updateProgressBar()
        }

        handler.post(runnable)
    }

    private fun updateProgressBar() {
        val currentProgress = mediaPlayer.currentPosition.toFloat()
        val totalDuration = mediaPlayer.duration.toFloat()

        val progress = ((currentProgress/totalDuration) * 100).toInt()
        progressBar.progress = progress

        handler.postDelayed(runnable, 100)
    }

    private fun restart() {
        mediaPlayer.seekTo(0)
        mediaPlayer.start()
    }

    private fun pause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    private fun play() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(runnable)
    }
}