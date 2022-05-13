package com.example.mediaplayer.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
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
) : Parcelable {
}