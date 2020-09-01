package com.android.example.myapplication.album.di

import androidx.lifecycle.ViewModel
import com.android.example.myapplication.album.AlbumSearchViewModel
import com.android.example.myapplication.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AlbumModule {

    @Binds
    @IntoMap
    @ViewModelKey(AlbumSearchViewModel::class)
    abstract fun bindViewModel(albumSearchViewModel: AlbumSearchViewModel): ViewModel
}