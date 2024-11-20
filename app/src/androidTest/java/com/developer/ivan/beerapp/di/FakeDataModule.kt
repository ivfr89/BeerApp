package com.developer.ivan.beerapp.di

import com.developer.ivan.beerapp.fake.BeerFakeDataRepository
import com.developer.ivan.di.DataModule
import com.developer.ivan.repository.BeerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataModule::class]
)
@Module
class FakeDataModule {

    @Singleton
    @Provides
    fun provideBeerRepository(): BeerRepository = BeerFakeDataRepository()
}
