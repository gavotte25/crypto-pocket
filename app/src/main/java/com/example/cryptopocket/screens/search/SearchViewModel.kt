package com.example.cryptopocket.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptopocket.domain.Currency

class SearchViewModel: ViewModel() {

    private val _currencyList = MutableLiveData<List<Currency>>()
    val currencyList: LiveData<List<Currency>>
        get() = _currencyList

    init {
        val dummy = Currency(
            "LTC", "USD", 188.721, 188.091,
            "https://cdn.coinhako.com/assets/wallet-ltc-e4ce25a8fb34c45d40165b6f4eecfbca2729c40c20611acd45ea0dc3ab50f8a6.png",
            "Litecoin"
        )
        val dummy2 = Currency(
            "DOGE", "USD", 0.42934, 0.4243,
            "https://cdn.coinhako.com/assets/wallet-doge-96d9e86f316569a4e3e7e5196916e63ae30aaf7b9ed75577b2c3c72d77c6e66c.png",
            "Dogecoin"
        )
        _currencyList.value = listOf(dummy, dummy2)

    }

}