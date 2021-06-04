package com.example.cryptopocket.screens.search

import android.app.Application
import androidx.lifecycle.*
import com.example.cryptopocket.database.CurrencyDatabase
import com.example.cryptopocket.domain.Currency
import com.example.cryptopocket.repository.CurrencyRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class SearchViewModel(app: Application): AndroidViewModel(app) {

    private val database = CurrencyDatabase.getInstance(app).currencyDatabaseDao

    private val repository = CurrencyRepository(database)

    private val allItems = repository.allCurrencies

    private val _displayList = MutableLiveData<List<Currency>>()

    val currencyList: LiveData<List<Currency>>
        get() = _displayList

    init {
        allItems.observeForever{
            _displayList.value = allItems.value
        }
        viewModelScope.launch {
            repository.refreshCurrencyData()
        }
    }

    fun filter(keyword: String?) {
        keyword?.apply {
            _displayList.value = allItems.value?.filter {
                it.name.contains(keyword, true) || it.base.contains(keyword, true)
            } ?: return@apply
        }
    }

    fun addToPocket(currency: Currency) {
        viewModelScope.launch {
            repository.addCurrencyToPocket(currency)
        }
    }

}

class SearchViewModelFactory(val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(app) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")
    }
}