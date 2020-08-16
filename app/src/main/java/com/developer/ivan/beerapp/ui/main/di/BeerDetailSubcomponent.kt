package com.developer.ivan.beerapp.ui.main.di

import com.developer.ivan.beerapp.di.PerFragment
import com.developer.ivan.beerapp.ui.main.fragments.BeerDetailFragment
import com.developer.ivan.beerapp.ui.main.fragments.BeerListFragment
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [BeerDetailModule::class])
interface BeerDetailSubComponent {

    fun inject(fragment: BeerDetailFragment)


    @Subcomponent.Factory
    interface Factory {
        fun create(): BeerDetailSubComponent
    }


}