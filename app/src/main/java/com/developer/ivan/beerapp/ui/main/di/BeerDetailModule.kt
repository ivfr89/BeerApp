package com.developer.ivan.beerapp.ui.main.di

import androidx.lifecycle.ViewModel
import com.developer.ivan.beerapp.di.ViewModelKey
import com.developer.ivan.beerapp.ui.main.fragments.BeerDetailFragmentArgs
import com.developer.ivan.beerapp.ui.main.fragments.BeerDetailViewModel
import com.developer.ivan.beerapp.ui.main.fragments.BeerListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class BeerDetailModule {

    @Binds
    @IntoMap
    @ViewModelKey(BeerDetailViewModel::class)
    abstract fun provideViewModel(viewModel: BeerDetailViewModel): ViewModel

}