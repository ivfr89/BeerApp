package com.developer.ivan.beerapp.di

import com.developer.ivan.beerapp.ui.main.di.BeerDetailSubComponent
import com.developer.ivan.beerapp.ui.main.di.BeerListSubComponent
import dagger.Module

@Module(subcomponents = [BeerListSubComponent::class, BeerDetailSubComponent::class])
interface AppSubcomponents