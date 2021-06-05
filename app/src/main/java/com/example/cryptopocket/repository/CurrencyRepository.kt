package com.example.cryptopocket.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptopocket.api.CoinHakoApiService
import com.example.cryptopocket.api.asDatabaseModel
import com.example.cryptopocket.api.parseCurrencyJsonResult
import com.example.cryptopocket.database.CurrencyDatabaseDao
import com.example.cryptopocket.database.PocketRecord
import com.example.cryptopocket.database.asDomainModel
import com.example.cryptopocket.domain.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

interface CurrencyRepository {
    suspend fun refreshCurrencyData()
    suspend fun addCurrencyToPocket(currency: Currency)
    suspend fun deleteCurrencyFromPocket(currency: Currency)
    val allCurrencies: LiveData<List<Currency>>
    val inPocketCurrencies: LiveData<List<Currency>>
}

class CurrencyRepositoryImpl(private val database: CurrencyDatabaseDao, private val api: CoinHakoApiService ): CurrencyRepository {

    override suspend fun refreshCurrencyData() {
        withContext(Dispatchers.IO) {
            try {
                if(database.countValidData() == 0) {
                    val jsonString = api.getCurrencies()
                    val currencyList = parseCurrencyJsonResult(JSONObject(jsonString)).asDatabaseModel()
                    database.insertCurrencies(*currencyList.toTypedArray())
                    database.deleteOutdatedData()
                }
            }
            catch (e: Exception){println(e.toString())}
        }
    }

    override val allCurrencies: LiveData<List<Currency>>  = Transformations.map(database.getAllCurrency()){
        it.asDomainModel()
    }

    override val inPocketCurrencies: LiveData<List<Currency>> = Transformations.map(database.getCurrenciesFromPocket()) {
        it.asDomainModel()
    }

    override suspend fun addCurrencyToPocket(currency: Currency) {
        withContext(Dispatchers.IO) {
            database.insertToPocket(PocketRecord(currency.base))
        }
    }

    override suspend fun deleteCurrencyFromPocket(currency: Currency) {
        withContext(Dispatchers.IO) {
            database.deleteFromPocket(PocketRecord(currency.base))
        }
    }
}