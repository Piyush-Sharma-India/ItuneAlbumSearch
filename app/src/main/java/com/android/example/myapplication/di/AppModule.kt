package com.android.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.android.example.myapplication.data.source.AlbumDataSource
import com.android.example.myapplication.data.source.local.AlbumDatabase
import com.android.example.myapplication.data.source.local.AlbumLocalDataSourceImpl
import com.android.example.myapplication.data.source.remote.AlbumRemoteDataSource
import com.android.example.myapplication.network.AlbumSearchService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

import javax.inject.Qualifier
import javax.inject.Singleton

@Module
object AppModule {

    private const val BASE_URL = "https://itunes.apple.com"

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AlbumRemoteDataSource

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AlbumLocalDataSource

    @Singleton
    @AlbumRemoteDataSource
    @Provides
    fun provideAlbumRemoteDataSource(albumSearchService: AlbumSearchService): AlbumDataSource {
        return AlbumRemoteDataSource(albumSearchService)
    }

    @Singleton
    @AlbumLocalDataSource
    @Provides
    fun provideAlbumLocalDataSource(
        database: AlbumDatabase,
        ioDispatcher: CoroutineDispatcher
    ): AlbumDataSource {
        return AlbumLocalDataSourceImpl(database.albumDao(), ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AlbumDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AlbumDatabase::class.java,
            "Albums.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideAlbumService(retrofit: Retrofit): AlbumSearchService {
        return retrofit.create(AlbumSearchService::class.java)
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO


    @Singleton
    @Provides
    fun getOkHttpClient(): OkHttpClient {

        val logging =
            HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Timber.tag("OkHttp").d(message)
                }
            })

        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}