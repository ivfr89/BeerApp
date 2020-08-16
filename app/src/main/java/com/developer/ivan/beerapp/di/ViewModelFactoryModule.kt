package com.developer.ivan.beerapp.di

import androidx.lifecycle.ViewModelProvider
import com.developer.ivan.beerapp.ui.main.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory





}