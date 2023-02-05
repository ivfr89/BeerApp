package com.developer.ivan.data.server.datasources

import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure

interface RemoteDataSource {
    suspend fun getBeers(page: Int, size: Int): Either<Failure, List<Beer>>
}
