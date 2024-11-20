package com.developer.ivan.interactors

import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure

interface Interactor<Params, Return> {

    suspend operator fun invoke(params: Params): Either<Failure, Return> {
        return execute(params)
    }

    suspend fun execute(params: Params): Either<Failure, Return>
}
