package com.developer.ivan.data.server.datasources

import com.developer.ivan.ConnectionManager
import com.developer.ivan.data.server.ApiService
import com.developer.ivan.data.server.JsonMapper
import com.developer.ivan.data.server.NetworkManager
import com.developer.ivan.data.server.entities.toDomain
import com.developer.ivan.datasources.RemoteDataSource
import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure
import com.developer.ivan.domain.toRight

class RetrofitDataSource(
    private val retrofit: ApiService,
    private val jsonMapper: JsonMapper,
    private val connectivityManager: ConnectionManager
) : RemoteDataSource,
    NetworkManager by NetworkManager.NetworkImplementation() {


    object ConnectionError : Failure.CustomFailure()

    override suspend fun getBeers(page: Int, size: Int): Either<Failure, List<Beer>> =
        when (connectivityManager.isConnected()) {
            true -> safeRequest(
                retrofit.getBeers(
                    page = page,
                    perPage = size
                )
            ) { response ->
                jsonMapper.getArray(response) {
                    convertJsonToBeers(it) {
                        entityBeer ->
                        entityBeer.toDomain().toRight()
                    }
                }
            }
            false -> Either.Left(ConnectionError)
        }
}
