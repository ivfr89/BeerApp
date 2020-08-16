package com.developer.ivan.beerapp.di

import android.app.Application
import androidx.room.Room
import com.developer.ivan.beerapp.data.db.DB
import com.developer.ivan.beerapp.data.db.datasources.RoomDataSource
import com.developer.ivan.beerapp.data.db.mapper.DBMapper
import com.developer.ivan.beerapp.data.server.ApiService
import com.developer.ivan.beerapp.data.server.LocalConnectivityManager
import com.developer.ivan.beerapp.data.server.datasources.RetrofitDataSource
import com.developer.ivan.beerapp.data.server.mapper.JsonMapper
import com.developer.ivan.beerapp.data.server.mapper.ServerMapper
import com.developer.ivan.beerapp.ui.mapper.UIMapper
import com.developer.ivan.data.ConnectionManager
import com.developer.ivan.data.datasources.LocalDataSource
import com.developer.ivan.data.datasources.RemoteDataSource
import com.developer.ivan.data.repositories.BeerRepository
import com.developer.ivan.data.repositories.BeerRepositoryImplementation
import com.developer.ivan.domain.Constants
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {


    @Singleton
    @Provides
    fun provideHttpClient() = OkHttpClient.Builder()
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
        @Named("base_url") baseUrl: String
    ) = Retrofit.Builder().client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(baseUrl)
        .build().create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideConnectionManager(app: Application) : ConnectionManager =
        LocalConnectivityManager(app)


    @Singleton
    @Provides
    fun provideBeerRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): BeerRepository = BeerRepositoryImplementation(localDataSource, remoteDataSource)

    @Singleton
    @Provides
    fun provideMoshi(
    ): Moshi = Moshi.Builder().build()


    @Singleton
    @Provides
    fun provideDbMapper() = DBMapper()

    @Singleton
    @Provides
    fun provideServerMapper() = ServerMapper()

    @Singleton
    @Provides
    fun provideJsonMapper(moshi: Moshi) = JsonMapper(moshi)


    @Singleton
    @Provides
    fun provideUIMapper() = UIMapper()


    @Singleton
    @Provides
    fun provideLocalDataSource(
        dbMapper: DBMapper,
        database: DB
    ): LocalDataSource = RoomDataSource(dbMapper, database)


    @Singleton
    @Provides
    fun provideRemoteDataSource(
        apiService: ApiService,
        serverMapper: ServerMapper,
        jsonMapper: JsonMapper,
        connectionManager: ConnectionManager
    ): RemoteDataSource = RetrofitDataSource(
        apiService,
        serverMapper,
        jsonMapper,
        connectionManager
    )

    @Singleton
    @Provides
    fun provideDatabase(
        application: Application
    ) = Room.databaseBuilder(application, DB::class.java, "db").fallbackToDestructiveMigration()
        .build()

}