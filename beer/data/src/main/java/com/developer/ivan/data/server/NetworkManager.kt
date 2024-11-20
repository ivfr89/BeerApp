package com.developer.ivan.data.server

import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure
import com.developer.ivan.domain.flatMap
import retrofit2.Response

interface NetworkManager {

    class ServerResponseException(val errorCode: Int, val message: String?) :
        Failure.CustomFailure()

    class UnexpectedServerError(val errorCode: Int, val message: String?) : Failure.CustomFailure()
    class EmptyBody : Failure.CustomFailure()

    suspend fun <T, R> safeRequest(
        callRequest: Response<T>,
        functionCall: (T) -> Either<Failure, R>
    ): Either<Failure, R>

    class NetworkImplementation : NetworkManager {

        override suspend fun <T, R> safeRequest(
            response: Response<T>,
            functionCall: (T) -> Either<Failure, R>
        ): Either<Failure, R> {
            return (
                (
                    if (response.isSuccessful) {
                        val body = response.body()

                        if (body != null) {
                            Either.Right(body)
                        } else {
                            Either.Left(EmptyBody())
                        }
                    } else {
                        when (response.code()) {
                            in 300..600 -> Either.Left(
                                ServerResponseException(
                                    response.code(),
                                    response.errorBody()?.string()
                                )
                            )
                            else -> Either.Left(
                                UnexpectedServerError(
                                    response.code(),
                                    response.errorBody()?.string()
                                )
                            )
                        }
                    }
                    ).flatMap { rightResult -> functionCall.invoke(rightResult) }
                )
        }
    }
}
