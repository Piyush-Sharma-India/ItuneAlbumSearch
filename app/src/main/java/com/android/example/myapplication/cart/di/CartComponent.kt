package com.android.example.myapplication.cart.di

import com.android.example.myapplication.cart.AlbumCartFragment
import dagger.Subcomponent

@Subcomponent(modules = [CartModule::class])
interface CartComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CartComponent
    }

    fun inject(fragmentAlbum: AlbumCartFragment)
}