package com.android.example.myapplication.di

import com.android.example.myapplication.repository.AlbumRepository
import com.android.example.myapplication.repository.DefaultAlbumRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModuleBinds {

    @Singleton
    @Binds
    abstract fun bindAlbumRepository(repository: DefaultAlbumRepository): AlbumRepository
}