package com.android.example.myapplication.repository

import android.util.Log
import com.android.example.myapplication.album.AlbumFilterType
import com.android.example.myapplication.data.Album
import com.android.example.myapplication.data.Result
import com.android.example.myapplication.data.source.AlbumDataSource
import com.android.example.myapplication.di.AppModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class DefaultAlbumRepository @Inject constructor(
    @AppModule.AlbumRemoteDataSource private val albumsRemoteDataSource: AlbumDataSource,
    @AppModule.AlbumLocalDataSource private val albumLocalDataSource: AlbumDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AlbumRepository {

    override suspend fun getAlbums(albumFilterType: AlbumFilterType): Result<List<Album>> {
        return withContext(ioDispatcher) {

            when (val localAlbums = albumLocalDataSource.getAlbums(albumFilterType)) {
                is Error -> {
                    getAlbumsDataFromRemote(albumFilterType)
                }
                is Result.Success -> {
                    Timber.tag("OkHttp").d("Local data : " + localAlbums.toString() + "")
                    Log.d("OkHttp", "Local data : " + localAlbums.toString() + "")
                    if (localAlbums.data.isEmpty() && albumFilterType != AlbumFilterType.CART_ITEMS) {
                        getAlbumsDataFromRemote(albumFilterType)
                    } else {
                        localAlbums
                    }
                }
                else -> {
                    Timber.tag("OkHttp").d("IllegalStateException")
                    Log.d("OkHttp", "IllegalStateException")
                    throw IllegalStateException()
                }
            }
        }
    }

    private suspend fun getAlbumsDataFromRemote(albumFilterType: AlbumFilterType): Result<List<Album>> {
        val remoteAlbums = albumsRemoteDataSource.getAlbums(albumFilterType)
        when (remoteAlbums) {
            is Result.Success -> {
                albumLocalDataSource.saveAlbums(remoteAlbums.data)
            }
        }
        Timber.tag("OkHttp").d("Remote data : " + remoteAlbums.toString() + "")
        Log.d("OkHttp", "Remote data : " + remoteAlbums.toString() + "")
        return remoteAlbums
    }

    override suspend fun saveAlbums(album: List<Album>) {
    }

    override suspend fun updateAlbum(album: Album) {
        albumLocalDataSource.updateAlbum(album)
    }
}