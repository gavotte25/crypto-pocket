package com.example.cryptopocket.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CurrencyDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(vararg currency: DatabaseCurrency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToPocket(record: PocketRecord)

    @Delete
    suspend fun deleteFromPocket(record: PocketRecord)

    @Query("SELECT COUNT(base) FROM currency_table WHERE DATETIME(update_date) > DATETIME('now', '-4 hours') ")
    suspend fun countValidData(): Int

    @Query("SELECT * FROM currency_view")
    fun getAllCurrency(): LiveData<List<FullInfoCurrency>>

    @Query("SELECT * FROM currency_view WHERE added = 1")
    fun getCurrenciesFromPocket(): LiveData<List<FullInfoCurrency>>

//    This function is used as a backup in case some currencies from API has disappeared
//    and new batch of inserted data cannot override outdated value
    @Query("DELETE FROM currency_table WHERE DATETIME(update_date) < DATETIME('now', '-1 days')")
    suspend fun deleteOutdatedData()
}