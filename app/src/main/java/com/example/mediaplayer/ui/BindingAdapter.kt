package com.example.mediaplayer.ui

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mediaplayer.R
import com.example.mediaplayer.data.model.Result
import com.example.mediaplayer.ui.music_player.PlayStatus
import com.example.mediaplayer.ui.overview.NetworkStatus
import com.example.mediaplayer.ui.overview.PlayListAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Result>?) {
    val adapter = recyclerView.adapter as PlayListAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("playButton")
fun playStatus(statusImageView: ImageView, status: PlayStatus?) {
    when (status) {
        PlayStatus.PLAY -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_baseline_pause)
        }
        PlayStatus.PAUSE -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_baseline_play_arrow)
        }
    }
}

@BindingAdapter("networkStatus")
fun bindStatus(statusImageView: ImageView, status: NetworkStatus?) {
    when (status) {
        NetworkStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        NetworkStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        NetworkStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
