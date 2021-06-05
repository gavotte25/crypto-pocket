package com.example.cryptopocket.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DatabaseCurrency::class, PocketRecord::class],
    views = [FullInfoCurrency::class],
    version = 1, exportSchema = false)
abstract class CurrencyDatabase: RoomDatabase() {
    abstract val currencyDatabaseDao: CurrencyDatabaseDao
}