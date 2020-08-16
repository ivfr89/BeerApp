package com.developer.ivan.beerapp.di

import com.developer.ivan.data.repositories.BeerRepository
import com.developer.ivan.usecases.GetBeerById
import com.developer.ivan.usecases.GetBeers
import com.developer.ivan.usecases.UpdateBeer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule {

    @Singleton
    @Provides
    fun provideGetBeers(beerRepository: BeerRepository) = GetBeers(beerRepository)

    @Singleton
    @Provides
    fun provideGetBeer(beerRepository: BeerRepository) = GetBeerById(beerRepository)

    @Singleton
    @Provides
    fun provideUpdateBeer(beerRepository: BeerRepository) = UpdateBeer(beerRepository)

}