package com.example.cryptopocket.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://www.coinhako.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL).build()

interface CoinHakoApiService {
    @GET("api/v3/price/all_prices_for_mobile")
    suspend fun getCurrencies(): String
}

object CoinHakoApi {
    val retrofitService: CoinHakoApiService by lazy { retrofit.create(CoinHakoApiService::class.java) }
}