package com.example.cryptopocket.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://www.coinhako.com/api/v3/price/all_prices_for_mobile"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL).build()

interface CoinHakoApiService {
    @GET()
    suspend fun getCurrencies(): List<NetworkCurrency>
}

object CoinHakoApi {
    val retrofitService: CoinHakoApiService by lazy { retrofit.create(CoinHakoApiService::class.java) }

}