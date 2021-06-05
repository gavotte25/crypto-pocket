package com.example.cryptopocket.api

import retrofit2.http.GET

interface CoinHakoApiService {
    @GET("api/v3/price/all_prices_for_mobile")
    suspend fun getCurrencies(): String
}