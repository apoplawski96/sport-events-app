package com.apoplawski.sporteventsapp.ui.playback

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.apoplawski.sporteventsapp.R
import com.apoplawski.sporteventsapp.data.model.Event
import com.apoplawski.sporteventsapp.ui.events.EventsViewModel
import com.apoplawski.sporteventsapp.ui.events.EventsViewModelFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_event_playback.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class EventPlaybackActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory : EventsViewModelFactory by instance()
    private lateinit var viewModel: EventsViewModel
    private lateinit var currentEvent : Event

    private var player : ExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_playback)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventsViewModel::class.java)

        fetchEvent()
    }


    private fun initializePlayer(){
        player  = ExoPlayerFactory.newSimpleInstance(applicationContext)
        playback_playerView.player = player
        val videoSource : MediaSource = buildMediaSource(currentEvent.videoUrl)
        player!!.playWhenReady = playWhenReady
        player!!.seekTo(currentWindow, playbackPosition)
        player!!.prepare(videoSource, false, false)
    }

    private fun releasePlayer(){
        if (player != null){
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.release()
            player = null
        }
    }

    private fun buildMediaSource(videoUrl : String) : MediaSource {
        val dataSourceFactory : DataSource.Factory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "DAZN-code-challenge"))
        return ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoUrl))
    }

    private fun fetchEvent(){
        currentEvent = intent.getParcelableExtra("EVENT_PLAYBACK")!!
        eventTitle_textView.text = currentEvent.title
        eventSubtitle_textView.text = currentEvent.subtitle
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onResume() {
        super.onResume()
        if (player == null) initializePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        releasePlayer()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        releasePlayer()
    }

}
