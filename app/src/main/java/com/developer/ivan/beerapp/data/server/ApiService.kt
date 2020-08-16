package com.developer.ivan.beerapp.data.server

import com.developer.ivan.domain.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

//    https://core.ac.uk:443/api-v2/articles/search/arist%C3%B3teles?page=1&pageSize=10&metadata=true&fulltext=false&citations=false&similar=false&duplicate=false&urls=true&faithfulMetadata=false&apiKey=Bkbr7yEazKR8iq0Ahn6cpoHuJMjNU41L

    @retrofit2.http.GET(Constants.Server.Beer.GET_BEERS)
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ) : Response<String>


}