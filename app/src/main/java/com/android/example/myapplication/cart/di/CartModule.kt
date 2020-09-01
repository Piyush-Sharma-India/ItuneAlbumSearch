package com.android.example.myapplication.cart.di

import androidx.lifecycle.ViewModel
import com.android.example.myapplication.cart.CartViewModel
import com.android.example.myapplication.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CartModule {

    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun bindViewModel(cartViewModel: CartViewModel): ViewModel
}