package com.example.cryptopocket.repository

import com.example.cryptopocket.api.CoinHakoApi
import com.example.cryptopocket.api.asDomainModel
import com.example.cryptopocket.domain.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class CurrencyRepository() {
    suspend fun fetchOnlineData(): List<Currency> {
        var currencyList: List<Currency>
        withContext(Dispatchers.IO) {
            currencyList = try {
                CoinHakoApi.retrofitService.getCurrencies().asDomainModel()
            } catch (e: Exception) {
//                throw e
                listOf()
            }
        }
        return currencyList
    }
}