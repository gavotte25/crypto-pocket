package com.example.cryptopocket

import android.app.Application
import androidx.lifecycle.*
import com.example.cryptopocket.database.CurrencyDatabase
import com.example.cryptopocket.database.CurrencyDatabaseDao
import com.example.cryptopocket.domain.Currency
import com.example.cryptopocket.repository.CurrencyRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(app: Application, private val repo: CurrencyRepository): AndroidViewModel(app) {

    private val allItems = repo.allCurrencies

    val pocketItems = repo.inPocketCurrencies

    private val _filteredItems = MutableLiveData<List<Currency>>()
    val filteredItems: LiveData<List<Currency>>
        get() = _filteredItems

    init {
        allItems.observeForever{
            _filteredItems.value = allItems.value
        }
        viewModelScope.launch {
            repo.refreshCurrencyData()
        }
    }

    fun removeFromPocket(currency: Currency) {
        viewModelScope.launch {
            repo.deleteCurrencyFromPocket(currency)
        }
    }

    fun addToPocket(currency: Currency) {
        viewModelScope.launch {
            repo.addCurrencyToPocket(currency)
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