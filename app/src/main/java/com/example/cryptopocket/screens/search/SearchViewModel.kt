package com.example.cryptopocket.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptopocket.domain.Currency
import com.example.cryptopocket.repository.CurrencyRepository
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    private val allItems = MutableLiveData<List<Currency>>()

    private val _displayList = MutableLiveData<List<Currency>>()

    private val repository = CurrencyRepository()

    val currencyList: LiveData<List<Currency>>
        get() = _displayList

    init {
        viewModelScope.launch {
            allItems.value = repository.fetchOnlineData()
        }
        _displayList.value = allItems.value
    }

    fun filter(keyword: String?) {
        keyword?.apply {
            _displayList.value = allItems.value?.filter {
                it.name.contains(keyword, true) || it.base.contains(keyword, true)
            } ?: return@apply
        }
    }

}