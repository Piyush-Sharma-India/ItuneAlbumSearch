package com.android.example.myapplication.album.di

import com.android.example.myapplication.album.AlbumSearchFragment
import dagger.Subcomponent

@Subcomponent(modules = [AlbumModule::class])
interface AlbumComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AlbumComponent
    }

    fun inject(fragment: AlbumSearchFragment)
}