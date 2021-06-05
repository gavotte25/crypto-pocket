package com.example.cryptopocket.utils

import android.content.Context
import androidx.room.Room
import com.example.cryptopocket.MainViewModel
import com.example.cryptopocket.api.CoinHakoApiService
import com.example.cryptopocket.database.CurrencyDatabase
import com.example.cryptopocket.database.CurrencyDatabaseDao
import com.example.cryptopocket.repository.CurrencyRepository
import com.example.cryptopocket.repository.CurrencyRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

val networkModule = module {
    fun provideRetrofit(): Retrofit {
        val baseUrl = "https://www.coinhako.com/"
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(baseUrl).build()
    }
    single { provideRetrofit() }
}

val apiModule = module {
    fun provideCoinHakoApi(retrofit: Retrofit): CoinHakoApiService{
        return retrofit.create(CoinHakoApiService::class.java)
    }
    single { provideCoinHakoApi(get()) }
}

val databaseModule = module {
    fun provideCurrencyDatabase(context: Context): CurrencyDatabase {
        return Room.databaseBuilder(context.applicationContext, CurrencyDatabase::class.java,"currency_database")
            .fallbackToDestructiveMigration().build()
    }

    fun provideCurrencyDatabaseDao(database: CurrencyDatabase): CurrencyDatabaseDao {
        return database.currencyDatabaseDao
    }
    single { provideCurrencyDatabase(androidApplication())}
    single { provideCurrencyDatabaseDao(get()) }
}

val repositoryModule = module {
    fun provideCurrencyRepository(database: CurrencyDatabaseDao, api: CoinHakoApiService): CurrencyRepository {
        return CurrencyRepositoryImpl(database, api)
    }
    single { provideCurrencyRepository(get(), get())}
}

val viewModelModule = module {
    viewModel{MainViewModel(get(), get())}
}