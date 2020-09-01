package com.android.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import java.text.SimpleDateFormat

@Entity(tableName = "albums")
data class Album(
    @PrimaryKey
    @ColumnInfo(name = "trackId")
    @Json(name = "trackId")
    val trackId: Long,

    @ColumnInfo(name = "artistName")
    @Json(name = "artistName")
    val artistName: String,

    @ColumnInfo(name = "collectionName")
    @Json(name = "collectionName")
    val collectionName: String?,

    @ColumnInfo(name = "trackName")
    @Json(name = "trackName")
    val trackName: String,

    @ColumnInfo(name = "collectionPrice")
    @Json(name = "collectionPrice")
    val collectionPrice: Double,

    @ColumnInfo(name = "releaseDate")
    @Json(name = "releaseDate")
    val releaseDateString: String,

    @ColumnInfo(name = "artworkUrl100")
    @Json(name = "artworkUrl100")
    val artworkURL: String,

    @ColumnInfo(name = "isAddedToCart") var isAddedToCart: Boolean = false
) {

    val releaseDateLong
        get() = run {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            inputFormat.parse(releaseDateString).time
        }

    val releaseDateFormatted
        get() = run {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val outputFormat = SimpleDateFormat("dd-MMM-yyyy")
            outputFormat.format(inputFormat.parse(releaseDateString))
        }
}