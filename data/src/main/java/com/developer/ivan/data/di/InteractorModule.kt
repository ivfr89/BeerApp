package com.developer.ivan.data.di

import com.developer.ivan.repository.BeerRepository
import com.developer.ivan.interactors.GetBeerById
import com.developer.ivan.interactors.GetBeerCount
import com.developer.ivan.interactors.GetBeers
import com.developer.ivan.interactors.UpdateBeer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class InteractorModule {

    @Singleton
    @Provides
    fun provideGetBeerCount(beerRepository: BeerRepository) = GetBeerCount(beerRepository)

    @Singleton
    @Provides
    fun provideGetBeers(getBeerCount: GetBeerCount, beerRepository: BeerRepository) =
        GetBeers(getBeerCount, beerRepository)

    @Singleton
    @Provides
    fun provideGetBeer(beerRepository: BeerRepository) = GetBeerById(beerRepository)

    @Singleton
    @Provides
    fun provideUpdateBeer(beerRepository: BeerRepository) = UpdateBeer(beerRepository)

}
