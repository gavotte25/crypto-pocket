package com.example.cryptopocket.screens.pocket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptopocket.domain.Currency

class PocketViewModel: ViewModel() {

    private val _isNavigatedToSearch = MutableLiveData<Boolean>()
    val isNavigatedToSearch: LiveData<Boolean>
        get() = _isNavigatedToSearch

    private val _currencyList = MutableLiveData<List<Currency>>()
    val currencyList: LiveData<List<Currency>>
        get() = _currencyList

    init {
        _isNavigatedToSearch.value = false
        val dummy = Currency(
            "LTC", "USD", 188.721, 188.091,
            "https://cdn.coinhako.com/assets/wallet-ltc-e4ce25a8fb34c45d40165b6f4eecfbca2729c40c20611acd45ea0dc3ab50f8a6.png",
            "Litecoin"
        )
        _currencyList.value = listOf(dummy)

    }

    fun doneNavigation() {
        _isNavigatedToSearch.value = false
    }

    fun navigateToSearch() {
        _isNavigatedToSearch.value = true
    }
}