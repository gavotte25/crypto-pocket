package com.example.cryptopocket.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptopocket.api.CoinHakoApi
import com.example.cryptopocket.api.asDatabaseModel
import com.example.cryptopocket.api.parseCurrencyJsonResult
import com.example.cryptopocket.database.CurrencyDatabaseDao
import com.example.cryptopocket.database.PocketRecord
import com.example.cryptopocket.database.asDomainModel
import com.example.cryptopocket.domain.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class CurrencyRepository(private val database: CurrencyDatabaseDao) {

    suspend fun refreshCurrencyData() {
        withContext(Dispatchers.IO) {
            if(database.countValidData() == 0) {
                val jsonString = CoinHakoApi.retrofitService.getCurrencies()
                val currencyList = parseCurrencyJsonResult(JSONObject(jsonString)).asDatabaseModel()
                database.insertCurrencies(*currencyList.toTypedArray())
                database.deleteOutdatedData()
            }
        }
    }

    val allCurrencies: LiveData<List<Currency>>  = Transformations.map(database.getAllCurrency()){
        it.asDomainModel()
    }

    val inPocketCurrencies: LiveData<List<Currency>> = Transformations.map(database.getCurrenciesFromPocket()) {
        it.asDomainModel()
    }

    suspend fun addCurrencyToPocket(currency: Currency) {
        withContext(Dispatchers.IO) {
            database.insertToPocket(PocketRecord(currency.base))
        }
    }

    suspend fun deleteCurrencyFromPocket(currency: Currency) {
        withContext(Dispatchers.IO) {
            database.deleteFromPocket(PocketRecord(currency.base))
        }
    }
}