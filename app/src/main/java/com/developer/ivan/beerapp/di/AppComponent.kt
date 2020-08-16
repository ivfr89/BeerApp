package com.developer.ivan.beerapp.di

import android.app.Application
import com.developer.ivan.beerapp.ui.main.MainActivity
import com.developer.ivan.beerapp.ui.main.di.BeerDetailSubComponent
import com.developer.ivan.beerapp.ui.main.di.BeerListSubComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppSubcomponents::class, AppModule::class, UseCasesModule::class, ViewModelFactoryModule::class])
interface AppComponent {


    val beerListSubcomponent: BeerListSubComponent.Factory
    val beerDetailSubcomponent: BeerDetailSubComponent.Factory


    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Application): AppComponent
    }
}