package com.developer.ivan.beerapp.ui.main.di

import android.os.Parcelable
import com.developer.ivan.beerapp.di.PerFragment
import com.developer.ivan.beerapp.ui.main.fragments.BeerListFragment
import dagger.BindsInstance
import dagger.Subcomponent

@PerFragment
@Subcomponent(modules = [BeerListModule::class])
interface BeerListSubComponent {

    fun inject(fragment: BeerListFragment)


    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance state: Parcelable?): BeerListSubComponent
    }


}