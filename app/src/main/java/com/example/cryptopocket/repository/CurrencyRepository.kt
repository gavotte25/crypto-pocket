package com.example.cryptopocket.repository

import com.example.cryptopocket.api.CoinHakoApi
import com.example.cryptopocket.api.asDomainModel
import com.example.cryptopocket.api.parseCurrencyJsonResult
import com.example.cryptopocket.domain.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class CurrencyRepository() {
    suspend fun fetchOnlineData(): List<Currency> {
        var currencyList: List<Currency>
        var jsonString = ""
        withContext(Dispatchers.IO) {
            jsonString = CoinHakoApi.retrofitService.getCurrencies()
            currencyList = parseCurrencyJsonResult(JSONObject(jsonString)).asDomainModel()
        }
        return currencyList
    }
}