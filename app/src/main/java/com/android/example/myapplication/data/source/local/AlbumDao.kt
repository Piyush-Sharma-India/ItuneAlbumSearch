package com.android.example.myapplication.data.source.local

import androidx.room.*
import com.android.example.myapplication.data.Album

@Dao
interface AlbumDao {

    @Query("SELECT * FROM ALBUMS")
    suspend fun getAllAlbumsByReleaseDate(): List<Album>

    @Query("SELECT * FROM ALBUMS ORDER BY artistName ASC")
    suspend fun getAllAlbumsByArtistName(): List<Album>

    @Query("SELECT * FROM ALBUMS ORDER BY trackName ASC")
    suspend fun getAllAlbumsByTrackName(): List<Album>

    @Query("SELECT * FROM ALBUMS ORDER BY collectionName ASC")
    suspend fun getAllAlbumsByCollectionName(): List<Album>

    @Query("SELECT * FROM ALBUMS where isAddedToCart = 1")
    suspend fun getCartAlbums(): List<Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAlbums(album: List<Album>)

    @Update
    suspend fun updateAlbum(album: Album)
}