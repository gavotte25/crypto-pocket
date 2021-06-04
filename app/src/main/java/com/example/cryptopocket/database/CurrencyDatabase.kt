package com.example.cryptopocket.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseCurrency::class, PocketRecord::class],
    views = [FullInfoCurrency::class],
    version = 1, exportSchema = false)
abstract class CurrencyDatabase: RoomDatabase() {
    abstract val currencyDatabaseDao: CurrencyDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: CurrencyDatabase? = null
        fun getInstance(context: Context): CurrencyDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CurrencyDatabase::class.java,
                    "currency_database").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}