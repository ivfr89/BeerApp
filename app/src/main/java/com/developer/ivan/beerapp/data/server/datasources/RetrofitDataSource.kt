package com.developer.ivan.beerapp.data.server.datasources

import com.developer.ivan.beerapp.data.server.ApiService
import com.developer.ivan.beerapp.data.server.NetworkManager
import com.developer.ivan.beerapp.data.server.mapper.JsonMapper
import com.developer.ivan.beerapp.data.server.mapper.ServerMapper
import com.developer.ivan.data.ConnectionManager
import com.developer.ivan.data.datasources.RemoteDataSource
import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure

class RetrofitDataSource(
    private val retrofit: ApiService,
    private val serverMapper: ServerMapper,
    private val jsonMapper: JsonMapper,
    private val connectivityManager: ConnectionManager
) : RemoteDataSource,
    NetworkManager by NetworkManager.NetworkImplementation() {


    object ConnectionError : Failure.CustomFailure()

    override suspend fun getBeers(page: Int, size: Int): Either<Failure, List<Beer>> =
        when(connectivityManager.isConnected()){
            true-> safeRequest(
                retrofit.getBeers(
                    page = page,
                    perPage = size)
            ) { listBeers ->
                jsonMapper.getArray(listBeers.b) { jsonArray ->
                    convertJsonToBeers(jsonArray) {entityBeer->
                        serverMapper.convertEntityBeerToDomain(
                            entityBeer
                        )
                    }
                }
            }
            false -> Either.Left(ConnectionError)
        }


}

