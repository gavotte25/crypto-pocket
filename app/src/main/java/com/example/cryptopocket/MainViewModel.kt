package com.example.cryptopocket

import android.app.Application
import androidx.lifecycle.*
import com.example.cryptopocket.database.CurrencyDatabase
import com.example.cryptopocket.domain.Currency
import com.example.cryptopocket.repository.CurrencyRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(app: Application): AndroidViewModel(app) {

    private val database = CurrencyDatabase.getInstance(app).currencyDatabaseDao

    private val repository = CurrencyRepository(database)

    private val allItems = repository.allCurrencies

    val pocketItems = repository.inPocketCurrencies

    private val _filteredItems = MutableLiveData<List<Currency>>()
    val filteredItems: LiveData<List<Currency>>
        get() = _filteredItems

    init {
        allItems.observeForever{
            _filteredItems.value = allItems.value
        }
        viewModelScope.launch {
            repository.refreshCurrencyData()
        }
    }

    fun removeFromPocket(currency: Currency) {
        viewModelScope.launch {
            repository.deleteCurrencyFromPocket(currency)
        }
    }

    fun addToPocket(currency: Currency) {
        viewModelScope.launch {
            repository.addCurrencyToPocket(currency)
        }
    }

    fun filter(keyword: String?) {
        keyword?.apply {
            _filteredItems.value = allItems.value?.filter {
                it.name.contains(keyword, true) || it.base.contains(keyword, true)
            } ?: return@apply
        }
    }
}

class MainViewModelFactory(val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}