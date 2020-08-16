package com.developer.ivan.beerapp.ui.main.di

import androidx.lifecycle.ViewModel
import com.developer.ivan.beerapp.di.ViewModelKey
import com.developer.ivan.beerapp.ui.main.fragments.BeerListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class BeerListModule {

    @Binds
    @IntoMap
    @ViewModelKey(BeerListViewModel::class)
    abstract fun provideViewModel(viewModel: BeerListViewModel): ViewModel

}