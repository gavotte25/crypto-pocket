package com.example.cryptopocket.screens.pocket

import android.app.Application
import androidx.lifecycle.*
import com.example.cryptopocket.database.CurrencyDatabase
import com.example.cryptopocket.domain.Currency
import com.example.cryptopocket.repository.CurrencyRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PocketViewModel(app: Application): AndroidViewModel(app) {

    private val database = CurrencyDatabase.getInstance(app).currencyDatabaseDao

    private val repository = CurrencyRepository(database)

    private val _isNavigatedToSearch = MutableLiveData<Boolean>()
    val isNavigatedToSearch: LiveData<Boolean>
        get() = _isNavigatedToSearch
    val currencyList = repository.inPocketCurrencies

    init {
        _isNavigatedToSearch.value = false
        viewModelScope.launch {
            repository.refreshCurrencyData()
        }
    }

    fun doneNavigation() {
        _isNavigatedToSearch.value = false
    }

    fun navigateToSearch() {
        _isNavigatedToSearch.value = true
    }

    fun removeFromPocket(currency: Currency) {
        viewModelScope.launch {
            repository.deleteCurrencyFromPocket(currency)
        }
    }
}

class PocketViewModelFactory(val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PocketViewModel::class.java)) {
            return PocketViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}