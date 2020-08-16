package com.developer.ivan.domain

object Constants{

    object Server{
        const val BASE_URL = "https://api.punkapi.com/v2/"

        const val DEFAULT_PAGE=1
        const val DEFAULT_SIZE=20

        object Beer {
            const val GET_BEERS = "beers/"
        }

        object OkHTTPConfig {
            const val timeOut = 15000L
        }

    }
}