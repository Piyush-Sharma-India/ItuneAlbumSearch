package com.android.example.myapplication.data.source

import com.android.example.myapplication.album.AlbumFilterType
import com.android.example.myapplication.data.Album
import com.android.example.myapplication.data.Result

interface AlbumDataSource {
    suspend fun getAlbums(albumFilterType: AlbumFilterType): Result<List<Album>>
    suspend fun saveAlbums(album: List<Album>)
    suspend fun updateAlbum(album: Album)
}