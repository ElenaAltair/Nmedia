package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import android.net.Uri

import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
const val YOUTUBE_VIDEO_ID = "Evfe8GEn33w"
const val YOUTUBE_PLAYLIST = "UCU3jy5C8MB-JvSw_86SFV2w"

class VideoActivity : AppCompatActivity() {

    // on below line we are creating
    // a variable for our video view.
    lateinit var videoView: VideoView

    // on below line we are creating
    // a variable for our video url.
    var videoUrl =
        "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val src:String = intent.getStringExtra(Intent.EXTRA_TEXT).toString()
        videoUrl = src

        // on below line we are initializing
        // our buttons with id.
        videoView = findViewById(R.id.videoView);

        // on below line we are creating
        // uri for our video view.
        val uri: Uri = Uri.parse(videoUrl)

        // on below line we are setting
        // video uri for our video view.
        videoView.setVideoURI(uri)

        // on below line we are creating variable
        // for media controller and initializing it.
        val mediaController = MediaController(this)

        // on below line we are setting anchor
        // view for our media controller.
        mediaController.setAnchorView(videoView)

        // on below line we are setting media player
        // for our media controller.
        mediaController.setMediaPlayer(videoView)

        // on below line we are setting media
        // controller for our video view.
        videoView.setMediaController(mediaController)

        // on below line we are
        // simply starting our video view.
        videoView.start()

    }
}