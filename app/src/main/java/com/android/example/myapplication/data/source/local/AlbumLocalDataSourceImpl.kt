package com.android.example.myapplication.data.source.local

import com.android.example.myapplication.album.AlbumFilterType
import com.android.example.myapplication.data.Album
import com.android.example.myapplication.data.Result
import com.android.example.myapplication.data.source.AlbumDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumLocalDataSourceImpl internal constructor(
    private val albumDao: AlbumDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AlbumDataSource {

    override suspend fun getAlbums(albumFilterType: AlbumFilterType): Result<List<Album>> =
        withContext(ioDispatcher) {
            return@withContext try {
                when (albumFilterType) {
                    AlbumFilterType.RELEASE_DATE -> {
                        Result.Success(albumDao.getAllAlbumsByReleaseDate())
                    }
                    AlbumFilterType.ARTIST_NAME -> {
                        Result.Success(albumDao.getAllAlbumsByArtistName())
                    }
                    AlbumFilterType.TRACK_NAME -> {
                        Result.Success(albumDao.getAllAlbumsByTrackName())
                    }
                    AlbumFilterType.COLLECTION_NAME -> {
                        Result.Success(albumDao.getAllAlbumsByCollectionName())
                    }
                    else -> {
                        Result.Success(albumDao.getCartAlbums())
                    }
                }

            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    override suspend fun saveAlbums(album: List<Album>) = withContext(ioDispatcher) {
        albumDao.saveAlbums(album)
    }

    override suspend fun updateAlbum(album: Album) = withContext(ioDispatcher) {
        albumDao.updateAlbum(album)
    }
}