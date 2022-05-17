package com.example.mediaplayer.ui.music_player

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mediaplayer.data.model.Result
import com.example.mediaplayer.databinding.MusicPlayerFragmentBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers


class MusicPlayerFragment : Fragment() {

    lateinit var viewModel: MusicPlayerViewModel
    private var seekBar: SeekBar? = null
    private var player: ExoPlayer? = null

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = MusicPlayerFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val selectedResult: Result = MusicPlayerFragmentArgs.fromBundle(arguments!!).resultItem

        val viewModelFactory = MusicPlayerViewModelFactory(selectedResult)
        viewModel = ViewModelProvider(
            this, viewModelFactory).get(MusicPlayerViewModel::class.java)
        binding.viewModel = viewModel
        seekBar = binding.seekBar

        initializePlayer(selectedResult)
        viewModel.status.observe(viewLifecycleOwner, Observer {
            when (it) {
                PlayStatus.PLAY -> {
                    player!!.play()
                }
                PlayStatus.PAUSE -> {
                    player!!.pause()
                }
            }
        })
        return binding.root
    }

    private fun initializePlayer(selectedResult: Result) {
        val mediaSource: MediaSource =
                initializeMediaSource(selectedResult)

        player = ExoPlayer.Builder(requireContext())
            .build()
            .also { exoPlayer ->
                exoPlayer.addMediaSource(mediaSource)
                exoPlayer.prepare()
            }
        playerControls(player!!)
    }

    private fun initializeMediaSource(selectedResult: Result): MediaSource {
        val mediaItem = MediaItem.Builder()
            .setUri(selectedResult.musicUrl!!.toUri())
            .build()
        return ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(mediaItem)
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            exoPlayer.release()
        }

        player = null
    }

    private fun playerControls(player: ExoPlayer) {
        playerListener(player)
        seekBarChangeListener(player)
    }

    private fun seekBarChangeListener(player: ExoPlayer) {
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            private var progressValue = 0
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                progressValue = seekBar.progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                seekBar.progress = progressValue
                player.seekTo(progressValue.toLong())
            }
        })
    }

    private fun playerListener(player: ExoPlayer) {
        player.addListener(object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                seekBar?.progress = player.currentPosition.toInt()
                seekBar?.max = player.duration.toInt()
                updatePlayerPositionProgress(player.currentPosition.toInt(),
                    player.duration.toInt())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        seekBar?.progress = player!!.currentPosition.toInt()
                    }, {
                    }, {
                    })
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == ExoPlayer.STATE_READY) {
                    seekBar?.progress = player.currentPosition.toInt()
                    seekBar?.max = player.duration.toInt()
                    updatePlayerPositionProgress(player.currentPosition.toInt(),
                        player.duration.toInt())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            seekBar?.progress = player!!.currentPosition.toInt()
                        }, {
                        }, {
                        })
                }
            }
        })
    }

    fun updatePlayerPositionProgress(currentPosition: Int, duratation: Int): Observable<Int> {
        return Observable.create { subscriber ->
            for (i in 0..duratation) {
                if (viewModel.status.value == PlayStatus.PLAY) {
                    subscriber.onNext(currentPosition)
                }
                Thread.sleep(1000)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }
}
