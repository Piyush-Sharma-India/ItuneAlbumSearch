package com.android.example.myapplication.data.source.remote

import android.util.Log
import com.android.example.myapplication.album.AlbumFilterType
import com.android.example.myapplication.data.Album
import com.android.example.myapplication.data.Result
import com.android.example.myapplication.data.source.AlbumDataSource
import com.android.example.myapplication.network.AlbumSearchService
import timber.log.Timber

class AlbumRemoteDataSource(private val albumSearchService: AlbumSearchService) : AlbumDataSource {

    override suspend fun getAlbums(albumFilterType: AlbumFilterType): Result<List<Album>> {
        return try {
            Timber.tag("OkHttp").d(albumSearchService.getAlbumSearchResults().toString() + "")
            Timber.tag("OkHttp")
                .d(albumSearchService.getAlbumSearchResults().results.toString() + "")
            Log.d("OkHttp", albumSearchService.getAlbumSearchResults().results.toString() + "")

            Result.Success(albumSearchService.getAlbumSearchResults().results)
        } catch (e: Exception) {
            Timber.tag("OkHttp").d(e.toString() + "")
            Log.d("OkHttp", e.toString() + "")
            Result.Error(e)
        }
    }

    override suspend fun saveAlbums(album: List<Album>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateAlbum(album: Album) {
        TODO("Not yet implemented")
    }
}