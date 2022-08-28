package com.developer.ivan.data.server

import com.developer.ivan.domain.Constants
import retrofit2.Response
import retrofit2.http.Query


interface ApiService {

    @retrofit2.http.GET(Constants.Server.Beer.GET_BEERS)
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<String>
}
