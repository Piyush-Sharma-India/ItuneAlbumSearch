package com.android.example.myapplication.di

import android.content.Context
import com.android.example.myapplication.album.di.AlbumComponent
import com.android.example.myapplication.cart.di.CartComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        AppModuleBinds::class,
        ViewModelBuilderModule::class,
        SubcomponentsModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun albumComponent(): AlbumComponent.Factory
    fun cartComponent(): CartComponent.Factory
}

@Module(subcomponents = [AlbumComponent::class, CartComponent::class])
object SubcomponentsModule