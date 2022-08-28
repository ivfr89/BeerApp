package com.developer.ivan.data.di

import android.app.Application
import android.content.Context
import com.developer.ivan.ConnectionManager
import com.developer.ivan.data.db.DB
import com.developer.ivan.data.db.datasources.RoomDataSource
import com.developer.ivan.data.repositories.BeerRepositoryImplementation
import com.developer.ivan.data.server.ApiService
import com.developer.ivan.data.server.JsonMapper
import com.developer.ivan.data.server.LocalConnectivityManager
import com.developer.ivan.data.server.datasources.RetrofitDataSource
import com.developer.ivan.datasources.LocalDataSource
import com.developer.ivan.datasources.RemoteDataSource
import com.developer.ivan.domain.Constants
import com.developer.ivan.repository.BeerRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .callTimeout(Constants.Server.OkHTTPConfig.timeOut, TimeUnit.MILLISECONDS)
        .connectTimeout(Constants.Server.OkHTTPConfig.timeOut, TimeUnit.MILLISECONDS)
        .build()


    @Singleton
    @Named("base_url")
    @Provides
    fun provideBaseUrl() = Constants.Server.BASE_URL

    @Singleton
    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        jsonConfiguration: Json,
        @Named("base_url") baseUrl: String
    ): ApiService = Retrofit.Builder().client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(jsonConfiguration.asConverterFactory(MediaType.parse("application/json")!!))
        .baseUrl(baseUrl)
        .build().create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideJsonConfiguration(): Json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = false
        isLenient = true
        useArrayPolymorphism = true
    }

    @Singleton
    @Provides
    fun provideConnectionManager(app: Application): ConnectionManager =
        LocalConnectivityManager(app)


    @Singleton
    @Provides
    fun provideBeerRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): BeerRepository = BeerRepositoryImplementation(localDataSource, remoteDataSource)

    @Singleton
    @Provides
    fun provideLocalDataSource(
        database: DB
    ): LocalDataSource = RoomDataSource(database)

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        apiService: ApiService,
        jsonMapper: JsonMapper,
        connectionManager: ConnectionManager
    ): RemoteDataSource = RetrofitDataSource(
        apiService,
        jsonMapper,
        connectionManager
    )

    @Singleton
    @Provides
    fun provideJsonMapper(
        moshi: Moshi
    ): JsonMapper = JsonMapper(moshi)

    @Singleton
    @Provides
    fun provideMoshi(
    ): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = DB.build(context)
}
