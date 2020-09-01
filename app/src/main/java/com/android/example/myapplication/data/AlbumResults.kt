package com.android.example.myapplication.data


import com.squareup.moshi.Json

data class AlbumResults(
    @Json(name = "resultCount") val resultCount: Int,
    @Json(name = "results") val results: List<Album>
)