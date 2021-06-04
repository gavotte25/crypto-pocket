package com.example.cryptopocket.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CurrencyDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(vararg currency: DatabaseCurrency)

    @Query("DELETE FROM currency_table")
    suspend fun deleteAllCurrencies()

    @Query("SELECT * FROM currency_table ORDER BY base")
    fun getAllCurrency(): LiveData<List<DatabaseCurrency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToPocket(record: PocketRecord)

    @Delete
    suspend fun deleteFromPocket(record: PocketRecord)

    @Query("SELECT tb1.* FROM currency_table as tb1 JOIN pocket_table as tb2 ON tb1.base = tb2.base ORDER BY tb1.base")
    fun getCurrenciesFromPocket(): LiveData<List<DatabaseCurrency>>

    @Query("SELECT COUNT(base) FROM currency_table WHERE DATETIME(update_date) > DATETIME('now', '-4 hours') ")
    fun countValidData(): Int

//    This function is used as a backup in case some currencies from API has disappeared
//    and new batch of inserted data cannot override outdated value
    @Query("DELETE FROM currency_table WHERE DATETIME(update_date) < DATETIME('now', '-1 days')")
    fun deleteOutdatedData()
}