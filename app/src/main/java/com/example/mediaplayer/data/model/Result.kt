package com.example.mediaplayer.data.model

import com.squareup.moshi.Json

data class Result(
    @Json(name = "artistName")
    val artistName: String?,
    @Json(name = "trackName")
    val trackName: String?,
    @Json(name = "artworkUrl100")
    val imageUrl: String?,
    @Json(name = "previewUrl")
    val musicUrl: String?,
    @Json(name = "trackId")
    val trackId: Long?
) {
}