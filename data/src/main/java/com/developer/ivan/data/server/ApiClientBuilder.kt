package com.developer.ivan.data.server

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.reflect.KClass

class ApiClientBuilder(
    baseUrl: String,
    okHttpClient: OkHttpClient
) {
    private val retrofit: Retrofit = Retrofit.Builder().client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    fun <T : Any> buildEndpoint(apiClass: KClass<T>): T {
        return retrofit.create(apiClass.java)
    }
}
